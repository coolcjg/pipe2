package com.cjg.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cjg.user.document.User;
import com.cjg.user.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
		
	
	public Map<String, Object> checkUserId(User user) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		User existUser = userRepo.findByUserId(user.getUserId());
		
		if(existUser == null) {
			returnMap.put("count", 0);
		}else {
			returnMap.put("count", 1);
		}
		
		return returnMap;
		
	}
	
	public Map<String, Object> insertUser(User user) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try{
			
			String salt = getSalt();
			String encPassword = getEncrypt(user.getPassword(), salt);
			
			user.setSalt(salt);
			user.setPassword(encPassword);
			user.setCreatedDate(LocalDateTime.now());
			
			User existUser = userRepo.findByUserId(user.getUserId());
			
			if(existUser == null) {
				String id = userRepo.save(user).get_id();
				returnMap.put("status", "200");
				returnMap.put("message", "success");
				returnMap.put("id", id);				
			}else {
				returnMap.put("status", "500");
				returnMap.put("message", "duplicated userId");				
			}
			
		}catch(Exception e) {
			log.error(e.getMessage());
			returnMap.put("status", "500");
			returnMap.put("message", e.getMessage());
		}
		
		return returnMap; 
	}
	
	public String getSalt() {	    
	    //1. Random, salt 생성
	    SecureRandom sr = new SecureRandom();
	    byte[] salt = new byte[20];
	
	    //2. 난수 생성
	    sr.nextBytes(salt);
	
	    //3. byte To String (10진수 문자열로 변경)
	    StringBuffer sb = new StringBuffer();
	    for(byte b : salt) {
	        sb.append(String.format("%02x", b));
	    }
	
	    return sb.toString();
	}
	
	public String getEncrypt(String pwd, String salt) {
		
		String result = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update((pwd + salt).getBytes());
			byte[] pwdSalt = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for(byte b : pwdSalt) {
				sb.append(String.format("%02x",  b));
			}
			
			result = sb.toString();
			
		}catch(NoSuchAlgorithmException e) {
			log.error("getEncrypt : " + e.getMessage());
		}
		
		return result;
	}
	
	public Map<String,Object> userSelect(User user) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		User resultUser = userRepo.findByUserId(user.getUserId());
		
		if(resultUser != null) {
			returnMap.put("status", "200");
			returnMap.put("message", "success");
			returnMap.put("user", resultUser);			
		}else {
			returnMap.put("status", "400");
			returnMap.put("message", "not exist user");			
		}
		
		return returnMap;
	}	
	
	public Map<String, Object> userUpdate(User user) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		User one = userRepo.findByUserId(user.getUserId());
		
		if(one != null) {
			one.setUserName(user.getUserName());
			one.setLastModifiedDate(LocalDateTime.now());
			userRepo.save(one);
			
			returnMap.put("status", "200");
			returnMap.put("message", "success");			
		}else {
			returnMap.put("status", "500");
			returnMap.put("message", "update fail");			
		}
				
		return returnMap;
	}
	
	public Map<String, Object> userDelete(User user) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		int result =  userRepo.deleteByUserId(user.getUserId());
		
		if(result == 1) {
			returnMap.put("status", "200");
			returnMap.put("message", "success");
			
		}else {
			returnMap.put("status", "400");
			returnMap.put("message", "fail");
		}
		
		return returnMap;
	}
	
	public Map<String, Object> userCount(User user) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		long result = userRepo.count();
		
		returnMap.put("status", "200");
		returnMap.put("message", "success");
		returnMap.put("count", result);
		
		return returnMap;
	}
	
	public Map<String, Object> userList() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createDate").descending());
		Page<User> page = userRepo.findAll(pageable);
		
		List<User> userList = page.getContent();
		
		if(userList != null) {
			returnMap.put("status", "200");
			returnMap.put("message", "success");
			returnMap.put("list", userList);
		}else {
			returnMap.put("status", "400");
			returnMap.put("message", "fail");
		}
		
		return returnMap;
		
	}
	


}


