package com.vamanos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.vamanos.entity.Users;
import com.vamanos.repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String input) {
		Users user = userRepository.findByUsername(input);
		System.out.println("User is ::: "+user);
		if (user == null)
			throw new BadCredentialsException("Bad credentials");

		new AccountStatusUserDetailsChecker().check(user);

		return user;
	}
}