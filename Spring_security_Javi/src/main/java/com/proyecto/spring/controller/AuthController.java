package com.proyecto.spring.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.spring.models.AuthRequest;
import com.proyecto.spring.models.AuthResponse;
import com.proyecto.spring.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
    	try {
    	    authenticationManager.authenticate(
    	            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    	    );
    	} catch (BadCredentialsException e) {
    	    throw new BadCredentialsException("Usuario invalido o contraseña incorrecta");
    	}
        String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(request.getUsername()));
        return new AuthResponse(token);
    }
    
    @GetMapping("/protected-data")
    public String getProtectedData(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hola, " + userDetails.getUsername() + "! esta ruta esta protegida.";
    }
}
