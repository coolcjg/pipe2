package com.cjg.user.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParam {
	
	private String birthDayParam;
	
	private int blockCount = 5;
	private int page = 0;
	
	private String[] checkItems;
	
	//검색조건
	private String searchType;
	private String searchText;	

}
