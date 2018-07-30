package com.limonnana.generic.controllers;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.limonnana.generic.entities.Loginuser;
import com.limonnana.generic.entities.User;
import com.limonnana.generic.repositories.UserRepository;

@RestController
// @RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String createUser(@RequestBody String user) {
		Gson gson = new Gson();
		User u = gson.fromJson(user, User.class);
		u = userRepository.save(u);
		System.out.println(u);
		return "Success";
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
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(@RequestBody String user) {
		Gson gson = new Gson();
		User u = gson.fromJson(user, User.class);
		u = userRepository.save(u);
		System.out.println(u);
		return "Success";
	}
	
	@RequestMapping(value = "/userList")
	public String getUserList(){
		List<User> userList = userRepository.findAll();
		Gson gson = new Gson();
		String usersJson = gson.toJson(userList);
		System.out.println(usersJson);
		return usersJson;
	}

	@RequestMapping(value = "/logintest", method = RequestMethod.POST)
	public String showWelcomePage(@RequestBody String loginuser) {
		System.out.println(loginuser);
		Gson g = new Gson();
		Loginuser lo = g.fromJson(loginuser, Loginuser.class);
		System.out.println("username: " + lo.getUsername() + " password: " + lo.getPassword());
		return "Success";

	}

}
