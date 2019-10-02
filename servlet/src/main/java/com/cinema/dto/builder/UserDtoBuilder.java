package com.cinema.dto.builder;

import com.cinema.dto.UserDto;

public class UserDtoBuilder {
	
	private String email;
	
    private String password;
    
    private String matchingPassword;
    
    private String username;
    
    private String tel;

	public UserDtoBuilder email(String email) {
		this.email = email;
		return this;
	}

	public UserDtoBuilder password(String password) {
		this.password = password;
		return this;
	}

	public UserDtoBuilder matchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
		return this;
	}

	public UserDtoBuilder username(String username) {
		this.username = username;
		return this;
	}

	public UserDtoBuilder tel(String tel) {
		this.tel = tel;
		return this;
	}
	
	public UserDto build() {
		return new UserDto(email, password, matchingPassword, username, tel);
	}
    
}
