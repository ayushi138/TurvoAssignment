package com.turvo.flash.sale.ws.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turvo.flash.sale.ws.dto.CompanyUserDTO;
import com.turvo.flash.sale.ws.dto.RegisterCompanyUserDTO;
import com.turvo.flash.sale.ws.io.entity.CompanyUserEntity;
import com.turvo.flash.sale.ws.repository.CompanyUserRepository;
import com.turvo.flash.sale.ws.service.CompanyUserService;
import com.turvo.flash.sale.ws.shared.AmazonSES;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {

	@Autowired
	CompanyUserRepository companyUserRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	AmazonSES amazonSES;
	
	@Override
	public CompanyUserDTO createUser(CompanyUserDTO companyUserDto) {
		
		CompanyUserEntity companyUserEntity = null;
		
		companyUserEntity = companyUserRepository.findByEmailAddress(companyUserDto.getEmailAddress());
		
		if(companyUserEntity != null) {
			throw new RuntimeException("Duplicate User Entity");
		}
		companyUserDto.setPassword(passwordEncoder.encode(companyUserDto.getPassword()));
		companyUserEntity = new CompanyUserEntity();
		BeanUtils.copyProperties(companyUserDto, companyUserEntity);
		
		companyUserEntity.setRegistered(false);
		companyUserEntity = companyUserRepository.save(companyUserEntity);
		companyUserDto = new CompanyUserDTO();
		BeanUtils.copyProperties(companyUserEntity,companyUserDto);
		return companyUserDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CompanyUserEntity userEntity = companyUserRepository.findByEmailAddress(username);
		
		if(userEntity == null)
			throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmailAddress(),userEntity.getPassword(),userEntity.isRegistered(),
				true, true,
				true,new ArrayList<>());
	}

	@Override
	public boolean sendEmailToCompanyUsers() {
		Iterable<CompanyUserEntity> users = companyUserRepository.findAll();
		CompanyUserDTO companyUserDto = null;
		
		for(CompanyUserEntity user:users) {
			companyUserDto = new CompanyUserDTO();
			BeanUtils.copyProperties(user,companyUserDto);
			
			amazonSES.sendFlassSaleEmail(companyUserDto);
		}
		return true;
	}

	@Override
	public boolean registerCompanyUser(RegisterCompanyUserDTO registerCompanyUserDTO) {
		CompanyUserEntity userEntity = companyUserRepository.findByEmailAddress(registerCompanyUserDTO.getUser());
		if(!passwordEncoder.matches(registerCompanyUserDTO.getPassword(),userEntity.getPassword())) {
			return false;
		}
		userEntity.setRegistered(true);
		companyUserRepository.save(userEntity);
		return true;
	}

}
