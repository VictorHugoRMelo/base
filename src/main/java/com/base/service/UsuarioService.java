package com.base.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.enuns.PerfilUsuarioEnum;
import com.base.exception.EmailException;
import com.base.exception.ErroAutenticacaoException;
import com.base.exception.NegocioException;
import com.base.jwt.JwtTokenProvider;
import com.base.model.Usuario;
import com.base.repository.UsuarioRepository;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtTokenProvider jwtTokenService;

	@Autowired
    private EmailService emailService;
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var usuario = findByEmail(username);
		if(Objects.nonNull(usuario)) {
			return User.builder()
					.username(usuario.getEmail())
					.password(usuario.getSenha())
					.roles(usuario.getPerfil().getNome())
					.build();
		}
		
		throw new NegocioException("usuario-nao-encontrado");
	}

    @Transactional
    public Usuario buscarPorId(Long id) {
    	return repository.findById(id).orElseThrow(() -> new NegocioException("usuario-nao-encontrado"));
    }
    
    @Transactional(rollbackFor = EmailException.class)
    public Usuario incluir(Usuario usuario) {
    	if (repository.findByEmail(usuario.getEmail()).isPresent()) {
			throw new NegocioException("usuario-ja-cadastrado");
		}
    	
    	usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setCadastroConfirmado(false);
		usuario.setAtivo(true);
		usuario.setPerfil(null);
    	
    	repository.save(usuario);
    	
    	enviaEmailCadastroUsuario(usuario);
    	
    	return usuario;
    }
    
    @Transactional(readOnly = true)
    private void enviaEmailCadastroUsuario(Usuario usuario) {
		try {
			var adms = repository.findAllByPerfil(PerfilUsuarioEnum.ADMIN.getId());

			emailService.enviaEmailCadastroUsuario(usuario, adms);
			emailService.enviaEmailCadastroUsuarioAdm(usuario, adms);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException("erro-enviar-mensagem", e);
		}

	}

    @Transactional
    public Usuario alterar(Usuario usuario) {
    	if (repository.findByEmail(usuario.getEmail()).isEmpty()) {
			throw new NegocioException("usuario-nao-cadastrado");
		}
    	return repository.save(usuario);
    }
    
    @Transactional
    public void deletar(Long id) {
    	if (!repository.existsById(id)) {
			throw new NegocioException("usuario-nao-encontrado");
		}
    	repository.deleteById(id);
    }

    public Page<Usuario> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Usuario findByEmail(String email) {
    	return repository.findByEmail(email).orElse(null);
    }

	public String autenticar(String email, String senha, boolean permanecerLogado) {
		var usuario = findByEmail(email);
		if(Objects.nonNull(usuario)) {
			boolean senhaValida = passwordEncoder.matches(senha, usuario.getSenha());
			if(senhaValida) {
				return jwtTokenService.gerarToken(usuario, permanecerLogado);
			} else {
				throw new ErroAutenticacaoException("usuario-senha-invalidas");
			}
		} else {
			throw new NegocioException("usuario-nao-encontrado");
		}
	}
    
}
