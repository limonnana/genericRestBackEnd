package com.limonnana.generic.controllers;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
    MongoTemplate mongoTemplate;
	
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
	
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable String id){
		userRepository.deleteById(id);
		
		String response = "{\"response\":\"Success\"}";
		return response;
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
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
	public String updatePassword(@RequestBody String loginUser) {
		Gson gson = new Gson();
		Loginuser u = gson.fromJson(loginUser, Loginuser.class);
		User user = getUserById(u.getId());
		user.setPassword(u.getPassword());
	    userRepository.save(user);
		System.out.println(u.getPassword() + " " + u.getId());
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
		
		String token = "";
		String userId  = "";
		String response = "";
		Gson g = new Gson();
		
		Loginuser lo = g.fromJson(loginuser, Loginuser.class);
	    User userFromDB = getUserByEmail(lo.getUsername());
		
		if(userFromDB != null && authenticate(userFromDB, lo)){
		
			token = generateString();
			UserSession userSession = new UserSession();
			userId = userFromDB.getId();
			userSession.setUserId(userId);
			userSession.setStartSession(new Date());
			userSession.setToken(token);
			userSessionRepository.save(userSession);
		   
	       response = "{\"response\":\"Success\", \"userId\":\"" +  userId +  "\", \"token\":" + "\"" + token+"\"}";
		}else{
			response =  "{\"response\":\"Failed\"}";
		}
		
		return response;

	}
	
	@RequestMapping(value = "/checkUserAlreadyRegistered/{email}", method = RequestMethod.GET)
	public boolean checkUserAlreadyRegistered(@PathVariable String email){
		System.out.println(" email: " + email);
		boolean result = false;
		
		User u = getUserByEmail(email);
		if(u != null && u.getEmail() != ""){
			result = true;
		}
		return result;
	}
	
	private User getUserByEmail(String email){
		
		User result = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		List<User> userList =  mongoTemplate.find(query, User.class);
		
		if(userList != null && userList.size() > 0 ){
			result = userList.get(0);
		}
		return result;
	}
	
    private User getUserById(String userId){
		
		User result = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(userId));
		List<User> userList =  mongoTemplate.find(query, User.class);
		
		if(userList != null && userList.size() > 0 ){
			result = userList.get(0);
		}
		return result;
	}
	
	private boolean authenticate(User u, Loginuser loginuser){
		boolean result = false;
		    
		if(u != null && loginuser != null){
			String pass = u.getPassword();
			
			if(u != null && loginuser.getPassword().equals(pass)){
				result = true;
			}
		}
			
		return result;
	}
	
	public static String generateString() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return  uuid;
    }

}
