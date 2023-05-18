package com.sanjay.SecurityJWT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

	
	@Bean
	public BCryptPasswordEncoder Encoder()
	{
		return new BCryptPasswordEncoder();
	}
}
