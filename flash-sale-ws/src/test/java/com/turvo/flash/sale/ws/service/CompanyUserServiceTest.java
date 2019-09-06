package com.turvo.flash.sale.ws.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.turvo.flash.sale.ws.dto.CompanyUserDTO;
import com.turvo.flash.sale.ws.dto.RegisterCompanyUserDTO;
import com.turvo.flash.sale.ws.exceptions.CompanyUserServiceException;
import com.turvo.flash.sale.ws.io.entity.CompanyUserEntity;
import com.turvo.flash.sale.ws.repository.CompanyUserRepository;
import com.turvo.flash.sale.ws.shared.AmazonSES;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CompanyUserServiceTest {
	
	@Autowired
	CompanyUserService companyUserService;
	
	@MockBean
	AmazonSES amazonSES;
	
	@MockBean
	CompanyUserRepository companyUserRepository;
	
	@MockBean
	BCryptPasswordEncoder passwordEncoder;
	
	@Test(expected = CompanyUserServiceException.class)
	public void testSendEmailWhenNoUsersPresent() {
		Iterable<CompanyUserEntity> entities = new ArrayList<>();
		Mockito.when(companyUserRepository.findAll()).thenReturn(entities);
		companyUserService.sendEmailToCompanyUsers();
	}	
	
	@Test
	public void testSendEmailWhenUsersPresent() {
		Iterable<CompanyUserEntity> entities = new ArrayList<>();
		((ArrayList<CompanyUserEntity>)entities).add(new CompanyUserEntity());
		
		Mockito.when(companyUserRepository.findAll()).thenReturn(entities);
		doNothing().when(amazonSES).sendFlassSaleEmail(any(CompanyUserDTO.class));;
		assertTrue(companyUserService.sendEmailToCompanyUsers());
	}
	
	@Test(expected = CompanyUserServiceException.class)
	public void testRegisterUserWithIncorrectId() {
		
		Mockito.when(companyUserRepository.findByEmailAddress(any(String.class))).thenReturn(null);
		companyUserService.registerCompanyUser(new RegisterCompanyUserDTO());
		
	}
	
	@Test(expected = CompanyUserServiceException.class)
	public void testRegisterUserWithIncorrectPassword() {
		
		RegisterCompanyUserDTO registerCompanyUserDto=new RegisterCompanyUserDTO();
		registerCompanyUserDto.setUser("pqr");
		registerCompanyUserDto.setPassword("pqr");
		
		CompanyUserEntity entity = new CompanyUserEntity();
		entity.setPassword("abc");
		
		Mockito.when(companyUserRepository.findByEmailAddress(any(String.class))).thenReturn(entity);
		companyUserService.registerCompanyUser(registerCompanyUserDto);
	}
	
	@Test
	public void testRegisterUserWithCorrectDetails() {
		
		RegisterCompanyUserDTO registerCompanyUserDto=new RegisterCompanyUserDTO();
		registerCompanyUserDto.setUser("pqr");
		registerCompanyUserDto.setPassword("pqr");
		
		CompanyUserEntity entity = new CompanyUserEntity();
		entity.setPassword("pqr");
		entity.setEmailAddress("pqr");
		CompanyUserEntity savedEntity = new CompanyUserEntity();
		savedEntity.setPassword("pqr");
		savedEntity.setRegistered(true);
		
		Mockito.when(companyUserRepository.findByEmailAddress(any(String.class))).thenReturn(entity);
		Mockito.when(companyUserRepository.save(any(CompanyUserEntity.class))).thenReturn(savedEntity);
		Mockito.when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
		
		assertTrue(companyUserService.registerCompanyUser(registerCompanyUserDto));
	}
}
