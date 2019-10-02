package com.cinema.entity;

import java.util.List;

import com.cinema.entity.builder.UserBuilder;

public class User {

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

	public User() {}
	
	public User(
			final long id,
			final String email,
			final String password,
			final List<Role> authorities, 
			final String username, 
			final String tel, 
			final List<Movie> watchedMovies,
			final List<Ticket> tickets, 
			final boolean accountNonExpired, 
			final boolean accountNonLocked, 
			final boolean credentialsNonExpired, 
			final boolean enabled) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.username = username;
		this.tel = tel;
		this.watchedMovies = watchedMovies;
		this.tickets = tickets;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
	}

	public List<Movie> getWatchedMovies() {
		return watchedMovies;
	}

	public void setWatchedMovies(List<Movie> watchedMovies) {
		this.watchedMovies = watchedMovies;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Role> getAuthorities() {
		return authorities;
	}

	public String getUsername() {
		return username;
	}

	public String getTel() {
		return tel;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setAuthorities(final List<Role> authorities) {
		this.authorities = authorities;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setTel(final String tel) {
		this.tel = tel;
	}

	public void setTickets(final List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void setAccountNonExpired(final boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(final boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(final boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}
	
	public static UserBuilder builder() {
		return new UserBuilder();
	}

}