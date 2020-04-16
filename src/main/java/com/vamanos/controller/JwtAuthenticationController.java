package com.vamanos.controller;

import com.vamanos.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.vamanos.model.JwtRequest;
import com.vamanos.model.JwtResponse;
import com.vamanos.service.CustomUserDetailsService;
import com.vamanos.util.JwtTokenUtil;

@RestController

@CrossOrigin

public class JwtAuthenticationController {

	@Autowired

	private AuthenticationManager authenticationManager;

	@Autowired

	private JwtTokenUtil jwtTokenUtil;

	@Autowired

	private CustomUserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder encoder;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@RequestParam("username") String userName, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) throws Exception {
		Users user = userDetailsService.getUserByUserName(userName);
		if(encoder.matches(user.getPassword(),encoder.encode(oldPassword))){
			user.setPassword(encoder.encode(newPassword));
			userDetailsService.updateUser(user);
		}else{
			throw new Exception("Old password is incorrect.");
		}
		return ResponseEntity.ok("Password reset successfully...!");
	}

}