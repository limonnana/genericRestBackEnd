package com.limonnana.generic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.limonnana.generic.entities.User;
import com.limonnana.generic.repositories.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
	
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value="/createuser/{userJson}")
	public User createUser(@PathVariable("userJson") String userJson){
		Gson gson =new Gson();
		User u = gson.fromJson(userJson, User.class);
		u = userRepository.save(u);
		System.out.println(u);
		return u;
	}

}
