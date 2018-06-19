package com.limonnana.generic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.limonnana.generic.entities.Loginuser;
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
	
	    
	    @RequestMapping(value="/logintest", method = RequestMethod.POST)
	    public String showWelcomePage(@RequestBody String loginobject){
	    	System.out.println(loginobject);
	    	Gson g = new Gson();
	    	Loginuser lo = g.fromJson(loginobject, Loginuser.class);
	        System.out.println("username: " + lo.getUsername() + " password: " + lo.getPassword());
	        return "Success";

}
	    
}
