package com.cjg.user.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	private String _id;
	private String userId;
	private String userName;
	
	private String salt;
	private String password;
	
	private String orgId;
	private String deptId;
	
	private int blockCount = 10;
	
	private String[] checkItems;
	
	@CreatedDate
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
    @Version
    private Long version;	

}
