package com.limonnana.generic.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.limonnana.generic.entities.Loginuser;
import com.limonnana.generic.entities.User;
import com.limonnana.generic.entities.UserSession;
import com.limonnana.generic.repositories.UserSessionRepository;

@RestController
@CrossOrigin("*")
public class SessionController {
	
	@Autowired
	UserSessionRepository userSessionRepository;
	
	@RequestMapping(value = "/getSession", method = RequestMethod.POST)
	public String getSession(@RequestBody String userSession) {
		System.out.println(userSession);
		Gson g = new Gson();
		UserSession us = g.fromJson(userSession, UserSession.class);
		String jsonSessionFromDb = "Session is null";
		Example<UserSession> example = Example.of(us);
		Optional<UserSession> userOptional = userSessionRepository.findOne(example);
		String token = "";
		if(userOptional.isPresent()){
		UserSession userSessionFromDb = userOptional.get();
		jsonSessionFromDb = g.toJson(userSessionFromDb);
		}
		System.out.println(jsonSessionFromDb);
		return jsonSessionFromDb;

	}
	

}
