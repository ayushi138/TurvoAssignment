package com.turvo.flash.sale.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.turvo.flash.sale.ws.service.CompanyUserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	private final CompanyUserService companyUserService;
	private final BCryptPasswordEncoder passwordEncoder;
	
	
	public WebSecurity(CompanyUserService companyUserService,BCryptPasswordEncoder passwordEncoder) {
		this.companyUserService=companyUserService;
		this.passwordEncoder=passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().
		antMatchers(HttpMethod.POST,"/company-user").permitAll().
		antMatchers(HttpMethod.GET,"/company-user/send-email").permitAll().
		antMatchers(HttpMethod.POST,"/company-user/register").permitAll().
		antMatchers(HttpMethod.POST,"/watch").permitAll().
		anyRequest().authenticated()
		.and().addFilter(getAuthenticationFilter()).addFilter(new AuthorizationFilter(authenticationManager()))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(companyUserService).passwordEncoder(passwordEncoder);
	}
	
	public AuthenticationFilter getAuthenticationFilter() {
		
		 AuthenticationFilter filter = null;
				
		try {
			filter=	new AuthenticationFilter(authenticationManager());
			filter.setFilterProcessesUrl("/company-user/login");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return filter;
	}
	
}