package com.base.jwt;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.base.exception.ErroAutenticacaoException;
import com.base.model.Usuario;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}") 
	private String secret;
	
	private Algorithm algorithm;
	
	@PostConstruct
	public void secretKeyGenerator() {
		algorithm = Algorithm.HMAC256(secret);
	}

	public String gerarToken(Usuario usuario, boolean permanecerLogado) {
		try {
			return JWT.create().withSubject(usuario.getEmail()) 
					.withIssuedAt(new Date())
					.withClaim("nome", usuario.getNome())
					.withClaim("perfil", usuario.getPerfil().getNome())
					.withExpiresAt(gerarDataExpiracao(permanecerLogado))
					.sign(algorithm);
		} catch (Exception e) {
			throw new ErroAutenticacaoException("erro-geracao-token", e);
		}
	}
	
	public boolean validarToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).build();
			verifier.verify(token);
			return true;
		} catch (SignatureVerificationException e) {
			throw e;
		} catch (TokenExpiredException e) {
			log.warn(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	public String obterEmail(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt.getSubject();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	private Instant gerarDataExpiracao(boolean permanecerLogado) {
		if(permanecerLogado) {
			return Instant.now().plus(5, ChronoUnit.DAYS);
		} else {
			return Instant.now().plus(30, ChronoUnit.MINUTES);
		}
	}
	
}
