package com.base.config;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.base.jwt.JwtTokenProvider;
import com.base.model.Usuario;
import com.base.service.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final UsuarioService usuarioService;
	
	private static final String JWT_REGEX = "^[A-Za-z0-9-_]+?\\.[A-Za-z0-9-_]+?\\.[A-Za-z0-9-_]+$";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
			throws ServletException, IOException {
		
		try {
			var token = obterToken(request);
			if(tokenValido(token)) {
				var email = jwtTokenProvider.obterEmail(token);
				var usuario = usuarioService.findByEmail(email);
				if(Objects.nonNull(usuario)) {
					setUserAuthentication(usuario);
				}
			}
		} catch (SignatureVerificationException e) {
			fecharSessao(request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		filterChain.doFilter(request, response);
	}

	private void fecharSessao(HttpServletRequest request) {
		var session = request.getSession(false);
		if (Objects.nonNull(session)) {
			session.invalidate();
		}
		SecurityContextHolder.clearContext();
	}
	
	private String obterToken(HttpServletRequest request) {
		var authorization = request.getHeader("Authorization");
		if(Objects.nonNull(authorization)) {
			return authorization.replace("Bearer ", "");
		}
		return null;
	}
	
	private void setUserAuthentication(Usuario usuario) {
		var userDetails = User.withUsername(usuario.getEmail())
				.password(usuario.getSenha())
				.roles(usuario.getPerfil().getNome())
				.build();
		var authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private boolean tokenValido(String token) {
		if (Objects.isNull(token) || token.isEmpty()) {
			return false;
		}
		return Pattern.matches(JWT_REGEX, token) && jwtTokenProvider.validarToken(token);
	}

}
