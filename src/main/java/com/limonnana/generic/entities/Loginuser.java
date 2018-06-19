package com.limonnana.generic.entities;

import java.io.Serializable;

public class Loginuser implements Serializable{
	

	private static final long serialVersionUID = 2955743771558339220L;
	private String username;
	private String password;
	
	
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
	
	

}
