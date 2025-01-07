package com.base.email;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.base.enuns.AmbienteEnum;
import com.base.exception.EmailException;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Component
public class EmailAdapter {

    private final JavaMailSender mailSender;
    
    @Value("${app.ambiente}")
    private String ambiente;

    @Value("classpath:images/logo_tcia.png")
    private Resource logoTcia;

    public void enviarEmail(String from, String to, String subject, String message) throws EmailException {
        try {
        	if(!ambiente.equals(AmbienteEnum.DEV.name())) {
	            var mimeMessage = mailSender.createMimeMessage();
	            var helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	
	            helper.setFrom(from);
	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(message, true);
	            helper.addInline("logoTcia", logoTcia.getFile());

            	mailSender.send(mimeMessage);
            }
            
            log.info("E-mail enviado para {}", to);
        } catch (MessagingException | IOException e) {
            log.error("Erro ao enviar e-mail para {}", to, e);
            throw new EmailException("Erro ao enviar e-mail", e);
        }
    }

    public void enviarComAnexo(String from, List<String> to, String subject, String message, File attachment) throws EmailException {
        try {
        	if(!ambiente.equals(AmbienteEnum.DEV.name())) {
	            var mimeMessage = mailSender.createMimeMessage();
	            var helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	
	            helper.setFrom(from);
	            helper.setTo(to.toArray(String[]::new));
	            helper.setSubject(subject);
	            helper.setText(message, true);
	            helper.addInline("logoTcia", logoTcia.getFile());
	
	            if (Objects.nonNull(attachment)) {
	                helper.addAttachment(attachment.getName(), attachment);
	            }

            	mailSender.send(mimeMessage);
            }
            
            log.info("E-mail com anexo enviado para {}", to);
        } catch (MessagingException | IOException e) {
            log.error("Erro ao enviar e-mail com anexo para {}", to, e);
            throw new EmailException("Erro ao enviar e-mail com anexo", e);
        }
    }
}
