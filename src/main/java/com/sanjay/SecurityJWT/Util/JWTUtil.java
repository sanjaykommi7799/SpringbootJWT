package com.sanjay.SecurityJWT.Util;

import java.security.Key;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	@Value("${app.secret}")
	private String secret;
	
	public String generateToken(String username)
	{
		Map<String,Object> claims=new HashMap<>();
		return generateToken(claims,username);
	}
	public String generateToken(Map<String,Object> claims,String username)
	{
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuer("sanjay")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(60)))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
			
	}
	public Key getKey()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}
	public Claims getClaims(String token)
	{
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
	}
	public String getUsername(String token)
	{
		return getClaims(token).getSubject();
	}
	public Date getExpDate(String token)
	{
		return getClaims(token).getExpiration();
	}
	public boolean isTokenExpired(String token)
	{
		Date date=getExpDate(token);
		return date.before(new Date());
	}
	public boolean ValidateToken(String token,String username)
	{
		String userNameInToken=getUsername(token);
		
	return (username.equals(userNameInToken)&& !isTokenExpired(token));
	}

}
