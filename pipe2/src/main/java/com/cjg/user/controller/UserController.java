package com.cjg.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cjg.user.document.SearchParam;
import com.cjg.user.document.User;
import com.cjg.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/insertUser")
	public Map insertUser(User user, SearchParam param){		
		return userService.insertUser(user, param);
	}
	
	@PostMapping("/checkUserId")
	public Map checkUserId(@RequestBody User user){
		return userService.checkUserId(user);
	}
	
	@GetMapping("/selectUser")
	public Map selectUser(User user) {
		return userService.selectUser(user);
	}	
	
	@PostMapping("/updateUser")
	public Map updateUser(User user) {
		return userService.updateUser(user);
	}
	
	@PostMapping("/deleteUser")
	public Map deleteUser(User user, SearchParam param) {
		return userService.deleteUser(user, param);
	}
	
	@GetMapping("/userCount")
	public Map userCount(@RequestBody User user) {
		return userService.userCount(user);
	}
	
	@GetMapping("/userList")
	public Map userList(User user, SearchParam param) {		
		return userService.userList(user, param);
	}
	


}
