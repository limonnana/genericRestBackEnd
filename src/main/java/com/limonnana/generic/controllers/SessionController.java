package com.limonnana.generic.controllers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.limonnana.generic.entities.UserSession;
import com.limonnana.generic.repositories.UserSessionRepository;

@RestController
@CrossOrigin("*")
public class SessionController {
	
	@Autowired
	private UserSessionRepository userSessionRepository;
	
	@Autowired
	private Environment env;
	
	private static final String COOKIE_LIFE_IN_SECONDS = "cookieLifeInSeconds";
	
	@RequestMapping(value = "/getSession", method = RequestMethod.POST)
	public String getSession(@RequestBody String userSession) {
		System.out.println(userSession);
		Gson g = new Gson();
		String jsonSessionFromDb = "";
		
		UserSession us = g.fromJson(userSession, UserSession.class);
		
		Example<UserSession> example = Example.of(us);
		Optional<UserSession> userOptional = userSessionRepository.findOne(example);
		
		if(userOptional.isPresent()){
		 UserSession userSessionFromDb = userOptional.get();
		if(userSessionFromDb != null && checkToken(us, userSessionFromDb)){
		  jsonSessionFromDb = g.toJson(userSessionFromDb);
		}
		}
		System.out.println(jsonSessionFromDb);
		return jsonSessionFromDb;

	}
	
	private boolean checkToken(UserSession us, UserSession userSessionFromDb){
		boolean result = false;
		
		long startDate = userSessionFromDb.getStartSession().getTime();
		
		long nowLong = new Date().getTime();
		
		long resultLong  = (nowLong - startDate)/1000;
		
		long cookieLifeInSeconds = Long.parseLong(env.getProperty(COOKIE_LIFE_IN_SECONDS));
		
		if( userSessionFromDb.getToken().equals(us.getToken()) && resultLong < cookieLifeInSeconds){
			result = true;
		}
		
		return result;
	}
	

}
