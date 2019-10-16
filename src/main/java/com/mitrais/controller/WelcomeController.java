package com.mitrais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String welcome(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/transaction";
	}
}