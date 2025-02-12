package com.proyecto.spring;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
		SecurityContextHolder.getContext().getAuthentication();
		return "hola mundo";
	}

}
