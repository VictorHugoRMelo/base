package com.base.utils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:messages.properties")
@ConfigurationProperties(prefix = "app")
public class MessagesProperties {

    private String acessarSistema;
    private String empresaPrincipal;
    private String cadastroMsg;
    private String ativacaoMsg;
    private String recuperarSenha;
    private String entreComCodigoAcima;
    private String acesseMsg;
    private String solicitacaoEmitida;
    private String desativacaoMsg;
    private String arquivoAgendadoComSucesso;
    
}
