package com.vamanos.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.vamanos.service.CustomUserDetailsService;
import com.vamanos.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component

public class JwtRequestFilter implements Filter//extends OncePerRequestFilter 
{

	@Autowired

	private CustomUserDetailsService jwtUserDetailsService;

	@Autowired

	private JwtTokenUtil jwtTokenUtil;

	//@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)

			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
				//"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrZWRhciIsImV4cCI6MTU3MDg5MzM0OSwiaWF0IjoxNTcwODc1MzQ5fQ.h_xa1GbW4tMrdPOxt9spytSOaYz7lKP-sfWDp91clyKMA41qoNsSJmu3vHOJ9aG6R-eyaWDKuEywZsZGcLoJpg";//

		String username = null;

		String jwtToken = null;
		
		String a = request.getMethod();

// JWT Token is in the form "Bearer token". Remove Bearer word and get

// only the Token

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

			jwtToken = requestTokenHeader.substring(7);

			try {

				username = jwtTokenUtil.getUsernameFromToken(jwtToken);

			} catch (IllegalArgumentException e) {

				System.out.println("Unable to get JWT Token");

			} catch (ExpiredJwtException e) {

				System.out.println("JWT Token has expired");

			}

		} else {

			//logger.warn("JWT Token does not begin with Bearer String");

		}

// Once we get the token validate it.

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

// if token is valid configure Spring Security to manually set

// authentication

			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken

						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

// After setting the Authentication in the context, we specify

// that the current user is authenticated. So it passes the

// Spring Security Configurations successfully.

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}

		}

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
		response.setHeader("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Authorization,Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Origin");
		response.setStatus(200);
		chain.doFilter(request, response);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		doFilterInternal((HttpServletRequest)request,(HttpServletResponse)response,chain);
		
	}

}
