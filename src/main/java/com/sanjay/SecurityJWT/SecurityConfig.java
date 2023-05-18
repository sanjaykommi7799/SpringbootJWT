package com.sanjay.SecurityJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sanjay.SecurityJWT.filter.SecurityFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	//this one is for throwing the error if user is unauthorized
	@Autowired
	private JWTAuthenticationEntryPoint authenticationEntryPoint;
	

	@Autowired
	private SecurityFilter securityFilter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	//for stateless we need to mention this as extra : it checks username and password 
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		//getting the credentials and encoding by loading loadbyUsername() in UserDetailsService
		DaoAuthenticationProvider user=new DaoAuthenticationProvider();
		user.setPasswordEncoder(passwordEncoder);
		user.setUserDetailsService(userDetailsService);
		
		return user;
	}
	
	//this method is for authorization means delegating the access here to acccess RESTAPI's
	@Bean
	public SecurityFilterChain configure(HttpSecurity http)throws Exception
	{
		
		http.csrf().disable()
		.authorizeHttpRequests(authorize->
		{
		authorize.requestMatchers("/JWTUser/save","/JWTUser/login").permitAll()
		.anyRequest().authenticated();
		
		
		
		}).exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//verify user from 2nd request
		.and()
		.addFilterAfter(securityFilter, UsernamePasswordAuthenticationFilter.class)
		;
		return http.build();
	}
	
	
}
