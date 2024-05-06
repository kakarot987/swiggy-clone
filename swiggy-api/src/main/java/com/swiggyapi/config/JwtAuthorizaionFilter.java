package com.swiggyapi.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
@Component
public class JwtAuthorizaionFilter implements Filter{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizaionFilter.class);
	
	private static final String SECRET_KEY= "MTIzNDU2Nzg=";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String authToken = httpRequest.getHeader("Authorization");
		logger.info("Authorization = "+authToken);
		String getToken = generateAuthorizationToken();
		
		if(StringUtils.isEmpty(authToken)) {
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.setHeader("access-type", "Bearer");
			httpResponse.setHeader("access-token", getToken);
			httpResponse.getWriter().write("Missing Authorization token in request");
			return ;
			
		}
		authToken = authToken.substring(7);
		logger.info("Authorization Status = "+getToken.equals(authToken));
		if(!getToken.equals(authToken)) {
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.setHeader("access-type", "Bearer");
			httpResponse.setHeader("access-token", getToken);
			httpResponse.getWriter().write("Authorization token in request header is not valid");
			return;
		}
		
		chain.doFilter(httpRequest, httpResponse);
		
	}



	private String generateAuthorizationToken() {
		
		String token = Jwts.builder().
				claim("name", "test").
				signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
		
		return token;
	}

}
