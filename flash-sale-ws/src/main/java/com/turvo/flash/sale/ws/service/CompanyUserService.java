package com.turvo.flash.sale.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.turvo.flash.sale.ws.dto.CompanyUserDTO;
import com.turvo.flash.sale.ws.dto.RegisterCompanyUserDTO;

public interface CompanyUserService extends UserDetailsService{
	
	CompanyUserDTO createUser(CompanyUserDTO companyUserDto);

	boolean sendEmailToCompanyUsers();

	boolean registerCompanyUser(RegisterCompanyUserDTO registerCompanyUserDTO);

}
