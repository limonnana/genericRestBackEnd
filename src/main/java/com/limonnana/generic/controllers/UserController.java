package com.limonnana.generic.controllers;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.limonnana.generic.entities.Loginuser;
import com.limonnana.generic.entities.User;
import com.limonnana.generic.entities.UserSession;
import com.limonnana.generic.repositories.UserRepository;
import com.limonnana.generic.repositories.UserSessionRepository;

import java.util.UUID;

@RestController
// @RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserSessionRepository userSessionRepository;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createUser(@RequestBody String user) {
		System.out.println(user);
		Gson gson = new Gson();
		User u = gson.fromJson(user, User.class);
		u = userRepository.save(u);
		System.out.println(u);
		
		return "{\"response\":\"Success\"}";
	}
	
	@RequestMapping(value = "/user/{id}")
	public String getUser(@PathVariable String id){
		User u = new User();
		u.setId(id);
		Optional<User> optional = userRepository.findById(id);
		u = optional.get();
		Gson gson = new Gson();
		String usersJson = gson.toJson(u);
		System.out.println(usersJson);
		return usersJson;
	}
	
	@RequestMapping(value = "/deleteUser/{id}")
	public String deleteUser(@PathVariable String id){
		userRepository.deleteById(id);
		
		return "Success";
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
	public String updateUser(@RequestBody String user) {
		Gson gson = new Gson();
		User u = gson.fromJson(user, User.class);
		u = userRepository.save(u);
		System.out.println(u);
		String response = "{\"response\":\"Success\"}";
		return response;
	}
	
	@RequestMapping(value = "/userList")
	public String getUserList(){
		List<User> userList = userRepository.findAll();
		Gson gson = new Gson();
		String usersJson = gson.toJson(userList);
		System.out.println(usersJson);
		return usersJson;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody String loginuser) {
		System.out.println(loginuser);
		Gson g = new Gson();
		Loginuser lo = g.fromJson(loginuser, Loginuser.class);
		User user = new User();
		user.setEmail(lo.getUsername());
		//user.setPassword(lo.getPassword());
		Example<User> example = Example.of(user);
		Optional<User> userOptional = userRepository.findOne(example);
		String token = "";
		String userId  = "";
		if(userOptional.isPresent()){
		User userFromDb = userOptional.get();
		token = generateString();
		UserSession userSession = new UserSession();
		userId = userFromDb.getId();
		userSession.setUserId(userId);
		userSession.setStartSession(new Date());
		userSession.setToken(token);
		userSessionRepository.save(userSession);
		}
		String response = "{\"response\":\"Success\", \"userId\":\"" +  userId +  "\", \"token\":" + "\"" + token+"\"}";
		System.out.println(response);
		return response;

	}
	
	public static String generateString() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return  uuid;
    }

}
