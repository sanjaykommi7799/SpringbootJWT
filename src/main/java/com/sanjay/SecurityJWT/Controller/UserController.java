package com.sanjay.SecurityJWT.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanjay.SecurityJWT.Entity.User;
import com.sanjay.SecurityJWT.Entity.UserRequest;
import com.sanjay.SecurityJWT.Entity.UserResponse;
import com.sanjay.SecurityJWT.Service.IUserService;
import com.sanjay.SecurityJWT.Util.JWTUtil;

@RestController
@RequestMapping(value="/JWTUser")
public class UserController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtil Util;
	//saving the user
	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user)
	{
		Integer Id=service.save(user);
		String message="User saved :"+Id+" successfully";
		return ResponseEntity.ok(message);
	}
	//validating and generating the token
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest)
	{
		//validate theun/pwd with database
		//if user is unautthorize then it triggers JWTAuthenticationEntryPoint class
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		
		//generate token if credetials is valid
		String token=Util.generateToken(userRequest.getUsername());
		return ResponseEntity.ok(new UserResponse(token,"Token generated Successfully!"));
	}
	
	@PostMapping("/welcome")
	public ResponseEntity<String> welcome(Principal p)
	{
		return ResponseEntity.ok("welcome"+p.getName());
	}
}
