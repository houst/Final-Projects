package com.cinema.entity;

public enum Role {
	ADMIN, USER;

	public String getAuthority() {
		return name();
	}

}
