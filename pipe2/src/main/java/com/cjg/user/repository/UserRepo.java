package com.cjg.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cjg.user.document.User;

public interface UserRepo extends MongoRepository<User, Integer> {
	
		
	int deleteByUserId(String userId);
	
	@Query
	User findByUserId(String userId);
	
	@Query
	User findByUserName(String userName);

	@Query("{ $or: [ {'userId':{$regex:?0}}, {'userName':{$regex:?0}} ] }")
	Page<User> finBydAll(String searchText, Pageable pageable);	
	
	@Query("{'userId':{$regex:?0}}")
	Page<User> findByUserIdLike(String userId, Pageable pageable);
	
	@Query("{'userName':{$regex:?0}}")
	Page<User> findByUserNameLike(String userName, Pageable pageable);	
}
