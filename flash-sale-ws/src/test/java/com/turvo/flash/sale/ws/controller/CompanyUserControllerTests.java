package com.turvo.flash.sale.ws.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.turvo.flash.sale.ws.dto.RegisterCompanyUserDTO;
import com.turvo.flash.sale.ws.model.request.RegisterCompanyUserModel;
import com.turvo.flash.sale.ws.service.CompanyUserService;
import com.turvo.flash.sale.ws.service.WatchService;

@WebMvcTest
@RunWith(SpringRunner.class)
public class CompanyUserControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CompanyUserService companyUserService;
	
	@MockBean
	private WatchService watchService;
	
	@Test
	public void sendEmail() {
		try {
		Mockito.when(companyUserService.sendEmailToCompanyUsers()).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company-user/send-email").accept(MediaType.APPLICATION_JSON);
		ResultActions actions = mockMvc.perform(requestBuilder);
		actions.andExpect(MockMvcResultMatchers.status().isOk());
		actions.andExpect(content().json("{\n" + 
				"    \"operationType\": \"SEND_EMAIL\",\n" + 
				"    \"operationStatus\": \"SUCCESS\"\n" + 
				"}"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void registerUser() {
		try {
		RegisterCompanyUserModel registerCompanyUserModel = new RegisterCompanyUserModel();
		registerCompanyUserModel.setUser("test");
		registerCompanyUserModel.setPassword("!@#$");
		
		RegisterCompanyUserDTO registerCompanyUserDTO = new RegisterCompanyUserDTO();
		registerCompanyUserDTO.setUser("test");
		registerCompanyUserDTO.setPassword("!@#$");
		
		Mockito.when(companyUserService.registerCompanyUser(registerCompanyUserDTO)).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company-user/register",registerCompanyUserModel).accept(MediaType.APPLICATION_JSON);
		ResultActions actions = mockMvc.perform(requestBuilder);
		actions.andExpect(MockMvcResultMatchers.status().isOk());
		actions.andExpect(content().json("{\n" + 
				"    \"operationType\": \"REGISTER_USER\",\n" + 
				"    \"operationStatus\": \"SUCCESS\"\n" + 
				"}"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
