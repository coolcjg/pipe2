package com.cjg.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
	
	private String jwtSecret = "cjgSecretcjgSecretcjgSecretcjgSecretcjgSecret";
	
	private long tokenSeconds = 86400;
	
	@Bean
	public JwtManager jwtManager() {
		return new JwtManager(jwtSecret, tokenSeconds);
	}

}
