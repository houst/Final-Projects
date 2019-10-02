package com.cinema.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirectController {
	
	@Value("${locale.default}")
    private String localeDef;
	
	@GetMapping({"/"})
	public String redirect() {
		return "redirect:/" + localeDef;
	}
	
}
