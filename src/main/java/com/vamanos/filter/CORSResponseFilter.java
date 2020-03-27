package com.vamanos.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class CORSResponseFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		
			HttpServletResponse res = (HttpServletResponse) response;
		    res.setHeader("Access-Control-Allow-Origin", "*");
		    res.setHeader("Access-Control-Allow-Credentials", "true");
		    res.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
		    res.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Authorization,Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Origin,test");
		    //res.setStatus(200);
		    chain.doFilter(request, res);


	    

		
	}



}
