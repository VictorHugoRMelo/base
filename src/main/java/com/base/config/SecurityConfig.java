package com.base.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.base.jwt.JwtTokenProvider;
import com.base.service.UsuarioService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("URL") 
	private String urlFrontEnd;
	
	@Bean
	JwtFilter jwtFilter(JwtTokenProvider jwtTokenProvider, UsuarioService usuarioService) {
		return new JwtFilter(jwtTokenProvider, usuarioService);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter) throws Exception {
        return httpSecurity
        		.csrf(AbstractHttpConfigurer::disable)
        		.cors(cors -> cors.configure(httpSecurity))
        		.sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 )
        		.authorizeHttpRequests(auth -> {
        			auth.requestMatchers(HttpMethod.POST, "/usuario").permitAll();
        			auth.requestMatchers("/autenticacao/**").permitAll();
        			auth.anyRequest().authenticated();
        		})
				.logout(logout -> logout.logoutUrl("/autenticacao/logout")
						.logoutSuccessHandler((request, response, authentication) -> {
							response.setStatus(HttpServletResponse.SC_OK);
							response.getWriter().write("Logoff realizado com sucesso!");
				}))
        		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        		.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(urlFrontEnd);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
