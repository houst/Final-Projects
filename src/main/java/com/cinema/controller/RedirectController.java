package com.cinema.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirectController {

	@Autowired
    private ServletContext servletContext;
	
	@GetMapping({"/"})
	public String redirect() {
		return "redirect:/en" + servletContext.getContextPath();
	}
	
}
