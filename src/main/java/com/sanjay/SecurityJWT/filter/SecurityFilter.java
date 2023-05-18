package com.sanjay.SecurityJWT.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sanjay.SecurityJWT.Util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtil util;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//we need to read the Authorization header from the request
		String token=request.getHeader("Authorization");
		if(token!=null)
		{
			//we need to do validation ,so we need subject that we are getting from JWTUtil class
			String username=util.getUsername(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				//now we need to load the data from db and cross check whether request credentials are leal or not
				//so we need UserDetailsService here 
				UserDetails usr=userDetailsService.loadUserByUsername(username);
				
				//validate the token
				boolean isValid=util.ValidateToken(token, usr.getUsername());
				if(isValid)
				{
					UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username, usr.getPassword(),usr.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
