package com.sanjay.SecurityJWT.Entity;

import lombok.Data;

@Data
public class UserRequest {
	
	private String Username;
	private String Password;
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	@Override
	public String toString() {
		return "UserRequest [Username=" + Username + ", Password=" + Password + "]";
	}
	public UserRequest(String username, String password) {
		super();
		Username = username;
		Password = password;
	}
	public UserRequest() {}
	

}
