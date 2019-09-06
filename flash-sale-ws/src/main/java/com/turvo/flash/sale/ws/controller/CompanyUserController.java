package com.turvo.flash.sale.ws.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turvo.flash.sale.ws.dto.CompanyUserDTO;
import com.turvo.flash.sale.ws.dto.RegisterCompanyUserDTO;
import com.turvo.flash.sale.ws.model.request.CompanyUserRequestModel;
import com.turvo.flash.sale.ws.model.request.RegisterCompanyUserModel;
import com.turvo.flash.sale.ws.model.response.CompanyUserResponse;
import com.turvo.flash.sale.ws.model.response.OperationResponse;
import com.turvo.flash.sale.ws.model.response.OperationStatus;
import com.turvo.flash.sale.ws.model.response.OperationType;
import com.turvo.flash.sale.ws.service.CompanyUserService;

/*
 * Exposes end point URLs for the entity Company User
 */

@RestController()
@RequestMapping("/company-user")
public class CompanyUserController {
	
	@Autowired
	CompanyUserService companyUserService;
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public CompanyUserResponse addCompanyUser(@RequestBody CompanyUserRequestModel companyUserRequestModel) {
		
		CompanyUserDTO companyUserDto = new CompanyUserDTO();
		
		BeanUtils.copyProperties(companyUserRequestModel, companyUserDto);
		
		companyUserDto = companyUserService.createUser(companyUserDto);
		
		CompanyUserResponse companyUserResponse = new CompanyUserResponse();
		BeanUtils.copyProperties(companyUserDto, companyUserResponse);
		
		return companyUserResponse;
		
	}
	
	@GetMapping(path = "/send-email",
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public OperationResponse sendEmailToCompanyUsers() {
	OperationResponse operationResponse = new OperationResponse();
	operationResponse.setOperationType(OperationType.SEND_EMAIL.name());
	boolean sentSuccessfully=companyUserService.sendEmailToCompanyUsers();
	if(sentSuccessfully) {
		operationResponse.setOperationStatus(OperationStatus.SUCCESS.name());
	}
	else {
		operationResponse.setOperationStatus(OperationStatus.ERROR.name());
	}
	return operationResponse;
	}

	@PutMapping(path = "/register",
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public OperationResponse registerCompanyUser(@RequestBody RegisterCompanyUserModel registerCompanyUserRequestModel) {
	OperationResponse operationResponse = new OperationResponse();
	operationResponse.setOperationType(OperationType.REGISTER_USER.name());
	RegisterCompanyUserDTO registerCompanyUserDTO = new RegisterCompanyUserDTO();
	BeanUtils.copyProperties(registerCompanyUserRequestModel, registerCompanyUserDTO);
	boolean registeredSuccessfully=companyUserService.registerCompanyUser(registerCompanyUserDTO);
	if(registeredSuccessfully) {
		operationResponse.setOperationStatus(OperationStatus.SUCCESS.name());
	}
	else {
		operationResponse.setOperationStatus(OperationStatus.ERROR.name());
	}
	return operationResponse;
	}
}
