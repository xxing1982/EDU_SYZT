package com.syzton.sunread.dto.user;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.syzton.sunread.model.user.User;

public class UserDTO {
	private User user;
	
	private OAuth2AccessToken token;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public OAuth2AccessToken getToken() {
		return token;
	}
	public void setToken(OAuth2AccessToken token) {
		this.token = token;
	}
	public UserDTO(User user, OAuth2AccessToken token) {
		super();
		this.user = user;
		this.token = token;
	}
	 
	
	
}
