package com.cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.entity.Role;
import com.cinema.exception.AuthorityNotFoundException;
import com.cinema.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	private static final String DEFAULT_ROLE = "USER";
	
	@Autowired
	private RoleService service;
	
	@GetMapping
	public List<Role> getAllRoles() {
		return service.findAll();
	}
	
	public Role getDefaultRoleForNewUser() throws AuthorityNotFoundException {
		return service.findByAuthority(DEFAULT_ROLE);
	}
	
}
