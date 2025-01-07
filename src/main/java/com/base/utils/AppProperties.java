package com.base.utils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	private String ambiente;
	private String urlSite;
    private String empresa;
    private String diretorioBkp;
    private String nomeSistema;
    private Integer qtdProcessamentos;
    
}
