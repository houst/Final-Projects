package com.cinema.dto;

import com.cinema.dto.builder.UserDtoBuilder;

public class UserDto {
	
	private String email;
	
    private String password;
    
    private String matchingPassword;
    
    private String username;
    
    private String tel;

	public UserDto() {}

	public UserDto(String email, String password, String matchingPassword, String username, String tel) {
		this.email = email;
		this.password = password;
		this.matchingPassword = matchingPassword;
		this.username = username;
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public String getUsername() {
		return username;
	}

	public String getTel() {
		return tel;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public static UserDtoBuilder builder() {
		return new UserDtoBuilder();
	}
    
}
