package com.cjg.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cjg.user.document.SearchParam;
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
	
	public Map<String, Object> insertUser(User user, SearchParam param) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try{
			
			String salt = getSalt();
			String encPassword = getEncrypt(user.getPassword(), salt);
			
			user.setSalt(salt);
			user.setPassword(encPassword);
			user.setCreatedDate(LocalDateTime.now());
			
			User existUser = userRepo.findByUserId(user.getUserId());
			
			if(existUser == null) {
				user.setBirthDay(LocalDateTime.parse(param.getBirthDayParam(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
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
	
	public Map<String,Object> selectUser(User user) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		User resultUser = userRepo.findByUserId(user.getUserId());
		returnMap.put("user", resultUser);	
		return returnMap;
	}	
	
	public Map<String, Object> updateUser(User user) {
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
	
	public Map<String, Object> deleteUser(User user, SearchParam param) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		int successCount=0;
		int failCount=0;
		List<String> failUserIdList = new ArrayList<String>();
		for(String userId : param.getCheckItems()) {
			user.setUserId(userId);
			
			int result =  userRepo.deleteByUserId(user.getUserId());
			
			if(result == 1) {
				successCount++;		
			}else {
				failCount++;
				failUserIdList.add(user.getUserId());
			}			
		}
		
		returnMap.put("status", "200");
		returnMap.put("successCount", successCount);
		returnMap.put("failCount", failCount);
		returnMap.put("failUserIdList", failUserIdList);
		
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
	
	public Map<String, Object> userList(User user, SearchParam param) {
			
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if(param.getSearchType().equals("")) {
			param.setSearchType("all");
		}		
		
		Pageable pageable = PageRequest.of(param.getPage()-1, param.getBlockCount(), Sort.by("createdDate").descending());
		
		Page<User> page;
		
		if(param.getSearchType().equals("all")) {
			page = userRepo.finBydAll(param.getSearchText(), pageable);
		}else if(param.getSearchType().equals("userId")) {
			page = userRepo.findByUserIdLike(param.getSearchText(), pageable);
		}else {
			page = userRepo.findByUserNameLike(param.getSearchText(), pageable);
		}

		long totalPage = page.getTotalPages();
		
		List<User> userList = page.getContent();
		
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("page", param.getPage());
		pageInfo.put("totalPage", totalPage);
		
		if(userList != null) {
			returnMap.put("status", "200");
			returnMap.put("message", "success");
			returnMap.put("list", userList);
			returnMap.put("pageInfo", pageInfo);
		}else {
			returnMap.put("status", "400");
			returnMap.put("message", "fail");
		}
		
		return returnMap;
		
	}
	


}


