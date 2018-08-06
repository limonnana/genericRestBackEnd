package com.limonnana.generic.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="userSession")
public class UserSession {
	
	@Id
	private String userId;
	private String username;
	private String token;
	private Date startSession;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getStartSession() {
		return startSession;
	}
	public void setStartSession(Date startSession) {
		this.startSession = startSession;
	}

}
