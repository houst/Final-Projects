package com.cinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema.entity.Role;
import com.cinema.exception.AuthorityNotFoundException;
import com.cinema.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository repository;
	
	public List<Role> findAll() {
		return repository.findAll();
	}
	
	public Role findByAuthority(String authority) throws AuthorityNotFoundException {
		return repository.findByAuthority(authority).orElseThrow(() -> new AuthorityNotFoundException(authority));
	}

}
