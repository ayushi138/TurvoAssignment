package com.turvo.flash.sale.ws.controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turvo.flash.sale.ws.dto.RegisterCompanyUserDTO;
import com.turvo.flash.sale.ws.model.request.RegisterCompanyUserModel;
import com.turvo.flash.sale.ws.service.CompanyUserService;

@WebMvcTest(controllers = CompanyUserController.class)
@RunWith(SpringRunner.class)
public class CompanyUserControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CompanyUserService companyUserService;
	
	@Test
	public void sendEmail() {
		try {
		Mockito.when(companyUserService.sendEmailToCompanyUsers()).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/send-email").accept(MediaType.APPLICATION_JSON);
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

			RegisterCompanyUserModel registerCompanyModel = new RegisterCompanyUserModel();
			registerCompanyModel.setUser("test");
			registerCompanyModel.setPassword("!@#$");

			Mockito.when(companyUserService.registerCompanyUser(any(RegisterCompanyUserDTO.class))).thenReturn(true);
			RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/users/register")
					.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(registerCompanyModel));
			ResultActions actions = mockMvc.perform(requestBuilder);
			actions.andExpect(MockMvcResultMatchers.status().isOk());
			actions.andExpect(content().json("{\n" + "    \"operationType\": \"REGISTER_USER\",\n"
					+ "    \"operationStatus\": \"SUCCESS\"\n" + "}"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
}
}