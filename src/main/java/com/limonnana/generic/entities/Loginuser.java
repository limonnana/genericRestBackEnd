package com.limonnana.generic.entities;

import java.io.Serializable;

public class Loginuser implements Serializable{
	

	private static final long serialVersionUID = 2955743771558339220L;
	private String username;
	private String id;
	private String password;
	private String token;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
