package com.cjg.common;

public class Util {
	
	public static long getTotalPage(int blockCount, long totalCount) {
		
		long totalPage=1;
		long a = totalCount / blockCount;
		long b = totalCount % blockCount;
		
		if(a != 0) {
			if(b == 0) {
				totalPage = a;
			}else {
				totalPage = a+1;
			}
		}
	
		return totalPage;
	}
	

}
