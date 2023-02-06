package com.cjg.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cjg.user.document.User;
import com.cjg.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/userInsert")
	public Map userInsert(@RequestBody User user){
		return userService.userInsert(user);
	}
	
	@PostMapping("/checkUserId")
	public Map checkUserId(@RequestBody User user){
		return userService.checkUserId(user);
	}
	
	@GetMapping("/userSelect")
	public Map userSelect(@RequestBody User user) {
		return userService.userSelect(user);
	}	
	
	@PostMapping("/userUpdate")
	public Map userUpdate(@RequestBody User user) {
		return userService.userUpdate(user);
	}
	
	@PostMapping("/userDelete")
	public Map userDelete(@RequestBody User user) {
		return userService.userDelete(user);
	}
	
	@GetMapping("/userCount")
	public Map userCount(@RequestBody User user) {
		return userService.userCount(user);
	}
	
	@GetMapping("/userList")
	public Map userList() {
		return userService.userList();
	}
	


}
