package com.cinema.entity.builder;

import java.util.List;

import com.cinema.entity.Movie;
import com.cinema.entity.Role;
import com.cinema.entity.Ticket;
import com.cinema.entity.User;

public class UserBuilder {
	
	private long id;
    
	private String email;
	
    private String password;
    
    private List<Role> authorities;
    
    private String username;
    
    private String tel;
    
    private List<Movie> watchedMovies;
    
    private List<Ticket> tickets;
	
    private boolean accountNonExpired;
	
    private boolean accountNonLocked;
	
    private boolean credentialsNonExpired;
	
    private boolean enabled;
    

	public UserBuilder watchedMovies(List<Movie> watchedMovies) {
		this.watchedMovies = watchedMovies;
		return this;
	}
    
    public UserBuilder id(Long id) {
    	this.id = id;
    	return this;
    }
    
    public UserBuilder email(String email) {
    	this.email = email;
    	return this;
    }
    
    public UserBuilder password(String password) {
    	this.password = password;
    	return this;
    }
    
    public UserBuilder authorities(List<Role> authorities) {
    	this.authorities = authorities;
    	return this;
    }
    
    public UserBuilder username(String username) {
    	this.username = username;
    	return this;
    }
    
    public UserBuilder tel(String tel) {
    	this.tel = tel;
    	return this;
    }
    
    public UserBuilder tickets(List<Ticket> tickets) {
    	this.tickets = tickets;
    	return this;
    }
    
    public UserBuilder accountNonExpired(Boolean accountNonExpired) {
    	this.accountNonExpired = accountNonExpired;
    	return this;
    }
    
    public UserBuilder accountNonLocked(Boolean accountNonLocked) {
    	this.accountNonLocked = accountNonLocked;
    	return this;
    }
    
    public UserBuilder credentialsNonExpired(Boolean credentialsNonExpired) {
    	this.credentialsNonExpired = credentialsNonExpired;
    	return this;
    }
    
    public UserBuilder enabled(Boolean enabled) {
    	this.enabled = enabled;
    	return this;
    }
    
    public User build() {
    	return new User(
    			id, 
    			email, 
    			password, 
    			authorities, 
    			username, 
    			tel,
    			watchedMovies,
    			tickets, 
    			accountNonExpired, 
    			accountNonLocked, 
    			credentialsNonExpired, 
    			enabled);
    }
    
}
