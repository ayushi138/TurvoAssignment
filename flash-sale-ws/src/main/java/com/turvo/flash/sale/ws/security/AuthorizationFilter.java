package com.turvo.flash.sale.ws.security;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.turvo.flash.sale.ws.io.entity.CompanyUserEntity;
import com.turvo.flash.sale.ws.repository.CompanyUserRepository;
import com.turvo.flash.sale.ws.shared.SpringAppContext;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{
	
	@Autowired
	SpringAppContext springAppContext;

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthenication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if(token != null) {
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
			String user = Jwts.parser()
			.setSigningKey(SecurityConstants.tokenSecret)
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
			if(user != null) {
				CompanyUserRepository companyUserRepository=(CompanyUserRepository)springAppContext.getBean("companyUserRepository");
				CompanyUserEntity findByEmailAddress = companyUserRepository.findByEmailAddress(user);
				if(findByEmailAddress.isRegistered())
				return new UsernamePasswordAuthenticationToken(user, null,new ArrayList<>());
			}
			return null;
		}
		return null;
	}

}
