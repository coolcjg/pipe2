package com.cjg.jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtController {
	
	private final JwtManager jwtManager;

	
	@GetMapping("/jwt/info/{token}")
	public JwtManager.TokenInfo jwtInfo(@PathVariable String token){
		JwtManager.TokenInfo tokenInfo = jwtManager.getTokenInfo(token);
		return tokenInfo;
	}

}
