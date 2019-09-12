package com.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinema.dto.UserDto;
import com.cinema.entity.Role;
import com.cinema.entity.User;
import com.cinema.exception.EmailExistsException;
import com.cinema.exception.EmailNotFoundException;
import com.cinema.exception.IdNotFoundException;
import com.cinema.repository.UserRepository;
import com.google.common.collect.ImmutableList;

import lombok.NonNull;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	public PasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(@NonNull String email) throws EmailNotFoundException {
		return findByEmail(email);	
	}
	
	public Iterable<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(long id) throws IdNotFoundException {
		return repository.findById(id).orElseThrow(() -> new IdNotFoundException("There isn't an user with that id: " + id));
	}
	
	public User findByEmail(@NonNull String email) throws EmailNotFoundException {
		return repository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("There isn't an user with that email adress: " + email));	
	}
	
	public User update(@NonNull User user) {
		return repository.save(user);
	}
	
	public User registerNewUserAccount(@NonNull UserDto userDto, @NonNull Role role) throws EmailExistsException {
		try {
			User newUser = toUser(userDto);
			newUser.setAuthorities(ImmutableList.of(role));
			return repository.save(newUser);
		} catch (Exception e) {
			throw new EmailExistsException("There is an user with that email adress: " + userDto.getEmail());
		}
	}
	
	private User toUser(@NonNull UserDto userDto) {
		return User.builder()
				.email(userDto.getEmail())
				.password(bcryptPasswordEncoder().encode(userDto.getPassword()))
				.username(userDto.getUsername())
				.tel(userDto.getTel())
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true)
				.build();
	}
	
}
