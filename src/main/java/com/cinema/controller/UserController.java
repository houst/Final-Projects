package com.cinema.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cinema.dto.UserDto;
import com.cinema.entity.Role;
import com.cinema.entity.User;
import com.cinema.exception.AuthorityNotFoundException;
import com.cinema.exception.EmailExistsException;
import com.cinema.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private RoleController roleController;
	
	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<String> registerUserAccount(@Valid UserDto userDto, BindingResult result)
			throws EmailExistsException, AuthorityNotFoundException {
		
		Optional.ofNullable(result.getFieldError()).ifPresent(e -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);});
	    
		Role role = roleController.getDefaultRoleForNewUser();
		
	    service.registerNewUserAccount(userDto, role);
	    
	    return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public Iterable<User> getAllUsers() {
	    return service.findAll();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("{user}")
	public ResponseEntity<String> userUpdate(
			@RequestParam String email,
			@RequestParam String name,
			@RequestParam String number,
			@RequestParam Map<String, String> checkboxes,
			@RequestParam("userId") User user) 
	{
		
		user.setEmail(email);
		user.setUsername(name);
		user.setTel(number);
		
//		Set<String> roles = Arrays.stream(Role.values())
//				.map(Role::name).collect(Collectors.toSet());
//		
//		user.getAuthorities().clear();
//		
//		for (String key : checkboxes.keySet()) {
//			if (roles.contains(key)) {
//				user.getAuthorities().add(Role.valueOf(key));
//			}
//		}
		
		service.update(user);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
