package com.base.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.base.email.EmailAdapter;
import com.base.email.HtmlTableGenerator;
import com.base.exception.EmailException;
import com.base.exception.SistemaException;
import com.base.model.Processamento;
import com.base.model.Usuario;
import com.base.utils.AppProperties;
import com.base.utils.MessagesProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String URL_SITE = "${url_site}";
    private static final String TITULO_TABELA = "${titulo_tabela}";
    private static final String TBODY_CONTENT = "${tbody_content}";
    private static final String MSM_RODAPE = "${msm_rodape}";
    
	private final EmailAdapter emailAdapter;
    private final MessagesProperties msmProperties;
    private final AppProperties appProperties;
    
    @Value("classpath:email/notificacao.html")
    private Resource templateEmail;

	public void enviaEmailCadastroAutorizado(Usuario usuario) throws SistemaException {
		try {
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Login", usuario.getEmail());
			htg.getDados().put("Nome", usuario.getNome());
			htg.getDados().put("Senha", usuario.getSenha());
			
			var titulo = String.format("Confirmação de cadastro %s.<br/> Acesso liberado. ${url_site}.",  appProperties.getEmpresa());
			var rodape = String.format("<a href=\"${url_site}\"> %s</a><br/>", msmProperties.getAcessarSistema());
			var html = template
					.replace(TITULO_TABELA, titulo)
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			var assunto = String.format("%s - %s", msmProperties.getCadastroMsg(), appProperties.getEmpresa());
			
			emailAdapter.enviarEmail(appProperties.getEmpresa(), usuario.getEmail(), assunto, html);
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}

	public void enviaEmailCadastroUsuarioAdm(Usuario usuario, List<Usuario> adms) throws SistemaException {
		try {
		var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Login", usuario.getEmail());
			htg.getDados().put("Nome", usuario.getNome());
			
			var titulo = "Novo pedido de acesso ao sistema dashboardClaro.<br/> Entre no sistema para avaliar o pedido ${url_site}.";
			var rodape = String.format("<a href=\"${url_site}\"> %s</a><br/>", msmProperties.getAcessarSistema());
			var html = template
					.replace(TITULO_TABELA, titulo)
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			var assunto = String.format("%s - %s", msmProperties.getCadastroMsg(), appProperties.getEmpresa());
	
			for (Usuario ui : adms) {
				emailAdapter.enviarEmail(appProperties.getEmpresa(), ui.getEmail(), assunto, html);
			}
			
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}

	public void enviaEmailCadastroUsuario(Usuario usuario, List<Usuario> adms) throws SistemaException {
		try {
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Login", usuario.getEmail());
			htg.getDados().put("Nome", usuario.getNome());
			
			var titulo = String.format(
					"Você fez um pedido de cadastro %s.<br/> Aguarde a liberação do seu cadastro por um de nossos administradores.<br/>. ${url_site}.",
					appProperties.getEmpresa());
			var rodape = String.format("<a href=\"${url_site}\"> %s</a><br/>", msmProperties.getAcessarSistema());
			var html = template
					.replace(TITULO_TABELA, titulo)
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			var assunto = String.format("%s - %s", msmProperties.getCadastroMsg(), appProperties.getEmpresa());
			
			emailAdapter.enviarEmail(appProperties.getEmpresa(), usuario.getEmail(), assunto, html);
			
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}

	public void enviaEmailRecuperacaoSenhaUsuario(Usuario usuario) throws SistemaException {
		try {
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Login", usuario.getEmail());
			htg.getDados().put("Nome", usuario.getNome());
			htg.getDados().put("Cód. recuperação", usuario.getIdConfirmacaoCadastro());
			
			var urlAltera = appProperties.getUrlSite() + "/paginas/alterar_senha.html";
			
			var titulo = String.format("%s  <a href=\"${url_site}\">TCIA</a>.", msmProperties.getSolicitacaoEmitida());
			var rodape = String.format("%s <a href=\"%s\">%s</a>", msmProperties.getAcesseMsg(), urlAltera, msmProperties.getEntreComCodigoAcima());
			
			var html = template
					.replace(TITULO_TABELA, titulo)
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			var assunto = String.format("%s - %s", msmProperties.getCadastroMsg(), appProperties.getEmpresa());
	
			emailAdapter.enviarEmail(appProperties.getEmpresa(), usuario.getEmail(),assunto, html);
			
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}

	public void enviaEmailUsuarioDesativado(Usuario usuario) throws SistemaException {
		try {
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Login", usuario.getEmail());
			htg.getDados().put("Nome", usuario.getNome());
			
			var html = template
					.replace(TITULO_TABELA, "Cadastro desativado")
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, "")
					.replace(URL_SITE, appProperties.getUrlSite());
			var assunto = String.format("%s - %s", msmProperties.getCadastroMsg(), appProperties.getEmpresa());
			
			emailAdapter.enviarEmail(appProperties.getEmpresa(), usuario.getEmail(),assunto, html);
			
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}

	public void enviaEmailUsuarioAtivado(Usuario usuario) throws SistemaException {
		try {
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Login", usuario.getEmail());
			htg.getDados().put("Nome", usuario.getNome());
			
			var rodape = String.format("<a href=\"${url_site}\"> %s</a><br/>", msmProperties.getAcessarSistema());
			
			var html = template
					.replace(TITULO_TABELA,"Cadastro ativado")
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			
			var assunto = String.format("%s - %s", msmProperties.getCadastroMsg(), appProperties.getEmpresa());
			
			emailAdapter.enviarEmail(appProperties.getEmpresa(), usuario.getEmail(),assunto, html);
			
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}

	public void enviaEmailRecuperaSenha(Usuario usuario) throws SistemaException {
		try {
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Login", usuario.getEmail());
			htg.getDados().put("Nome", usuario.getNome());
			htg.getDados().put("Senha", usuario.getSenha());
			
			var rodape = String.format("<a href=\"${url_site}\"> %s</a><br/>", msmProperties.getAcessarSistema());
			
			var html = template
					.replace(TITULO_TABELA,"Cadastro ativado")
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			
			var assunto = String.format("Recuperação de senha -  %s", appProperties.getEmpresa(), html);
			
			emailAdapter.enviarEmail(appProperties.getEmpresa(), usuario.getEmail(), assunto, html);
			
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}

	public void enviarEmailProcessamentoRealizado(Processamento p, String assunto, LocalDateTime dataINI, LocalDateTime dataFIM) throws SistemaException {
		try {
			var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
	
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			
			var htg = new HtmlTableGenerator();
			if (Objects.nonNull(dataINI)) {
				htg.getDados().put("Início às", dataINI.format(sdf));
			} else {
				htg.getDados().put("Início às", "N/A");
			}
			if (Objects.nonNull(dataFIM)) {
				htg.getDados().put("Término às", dataFIM.format(sdf));
			} else {
				htg.getDados().put("Término às", "N/A");
			}
			htg.getDados().put("Nome arquivo", p.getNomeArquivo());
			
			var rodape = String.format("<a href=\"${url_site}\"> %s</a><br/>", msmProperties.getAcessarSistema());
			
			var html = template
					.replace(TITULO_TABELA,"Processamento de arquivo realizado.")
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			
			emailAdapter.enviarEmail(appProperties.getEmpresa(), p.getUsuario().getEmail(), assunto, html);
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}

	public void enviaEmailArquivoComErro(String assunto, String descricao, Usuario dest, String nomeArq, List<String> linhasComErro, 
			LocalDateTime dataINI, LocalDateTime dataFIM) throws SistemaException {
		try {
			var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
			
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Usuário", dest.getNome());
			htg.getDados().put("Email", dest.getEmail());
			htg.getDados().put("Arquivo", nomeArq);
			
			if(Objects.nonNull(dataINI)) {
				htg.getDados().put("Início às", dataINI.format(sdf));
			} else {
				htg.getDados().put("Início às", "N/A");
			}
			if(Objects.nonNull(dataFIM)) {
				htg.getDados().put("Término às", dataFIM.format(sdf));
			} else {
				htg.getDados().put("Término às", "N/A");
			}
			htg.getDados().put("Descrição", descricao);
			htg.getDados().put("Linhas erro", getLinhas(linhasComErro));
			
			var rodape = "ATENÇÃO: Este erro não evitou o tratamento do arquivo. Tente realizar um novo upload o mais rápido possível";
			
			var html = template
					.replace(TITULO_TABELA,"Sua planilha possui muitas linhas inválidas.")
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			
			
			emailAdapter.enviarEmail(appProperties.getEmpresa(), dest.getEmail(), "Erro " + assunto, html);
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}
	
	public void enviaEmailErroGenerico(Usuario dest, String nomeArq) throws SistemaException {
		try {
			var template = Files.readString(templateEmail.getFile().toPath(), StandardCharsets.UTF_8);
			var htg = new HtmlTableGenerator();
			htg.getDados().put("Usuário", dest.getNome());
			htg.getDados().put("Email", dest.getEmail());
			htg.getDados().put("Arquivo", nomeArq);
			
			var rodape = "ATENÇÃO: Este erro só pode ser visto pela equipe de manutenção. Entre em contato e repasse esse e-mail aos mesmos.";
			
			var html = template
					.replace(TITULO_TABELA,"Aconteceu um erro não previsto na importação do arquivo")
					.replace(TBODY_CONTENT, htg.montarBody())
					.replace(MSM_RODAPE, rodape)
					.replace(URL_SITE, appProperties.getUrlSite());
			
			emailAdapter.enviarEmail(appProperties.getEmpresa(), dest.getEmail(), "Erro genérico", html);
		
		} catch (EmailException e) {
			throw e;
		} catch (Exception e) {
			throw new SistemaException(e);
		}
	}
	
	private String getLinhas(List<String> linhasComErro) {
	    Objects.requireNonNull(linhasComErro, "A lista de linhas com erro não pode ser nula.");

	    var ret = new StringBuilder();
	    int i = 0;

	    for (String linha : linhasComErro) {
	        if (Objects.isNull(linha)) {
	            continue;
	        }
	        ret.append("%s<br/>".formatted(linha));
	        i++;

	        if (i >= 100) {
	            ret.append("... (Exibindo apenas as 100 primeiras linhas)<br/>");
	            break;
	        }
	    }

	    return ret.toString();
	}

   
}
