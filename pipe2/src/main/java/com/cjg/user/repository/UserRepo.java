package com.cjg.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cjg.user.document.User;

public interface UserRepo extends MongoRepository<User, Integer> {
	
	int deleteByUserId(String userId);
	
	@Query
	User findByUserId(String userId);
}
