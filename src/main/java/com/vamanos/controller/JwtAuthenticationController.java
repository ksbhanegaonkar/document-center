package com.vamanos.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.entity.Users;
import com.vamanos.model.JwtRequest;
import com.vamanos.model.JwtResponse;
import com.vamanos.model.PasswordResetRequest;
import com.vamanos.service.CustomUserDetailsService;
import com.vamanos.util.JsonUtil;
import com.vamanos.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin

public class JwtAuthenticationController {

	private final String CREDENTIAL_EXPIRE_MESSAGE = "User credentials have expired";
	private final String INVALID_CREDENTIAL_MESSAGE = "Bad credentials";

	@Autowired

	private AuthenticationManager authenticationManager;

	@Autowired

	private JwtTokenUtil jwtTokenUtil;

	@Autowired

	private CustomUserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder encoder;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){

		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(new JwtResponse(token));
		} catch (InternalAuthenticationServiceException e) {
			ObjectNode node = JsonUtil.getEmptyJsonObject();
			if (CREDENTIAL_EXPIRE_MESSAGE.equals(e.getMessage())) {
				node.put("message", "Credential Expired");
			} else if (INVALID_CREDENTIAL_MESSAGE.equals(e.getMessage())) {
				node.put("message", "Invalid Credential");
			} else {
				node.put("message", "Error occurred");
			}
			return ResponseEntity.ok(node);
		}
	}

	private void authenticate(String username, String password){
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception {
		Users user = userDetailsService.getUserByUserName(passwordResetRequest.getUsername());
		ObjectNode node = JsonUtil.getEmptyJsonObject();
		if(encoder.matches(passwordResetRequest.getOldPassword(),user.getPassword())){
			user.setPassword(encoder.encode(passwordResetRequest.getNewPassword()));
			user.setCredentialsNonExpired(false);
			userDetailsService.updateUser(user);
			node.put("message","success");
		}else{
			node.put("message","failed");
		}


		return ResponseEntity.ok(node);
	}

}