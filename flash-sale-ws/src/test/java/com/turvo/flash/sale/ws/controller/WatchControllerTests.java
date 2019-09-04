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

import com.turvo.flash.sale.ws.dto.OrderWatchDTO;
import com.turvo.flash.sale.ws.model.request.OrderWatchRequestModel;
import com.turvo.flash.sale.ws.service.CompanyUserService;
import com.turvo.flash.sale.ws.service.WatchService;

@WebMvcTest
@RunWith(SpringRunner.class)
public class WatchControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CompanyUserService companyUserService;
	
	@MockBean
	private WatchService watchService;
	
	
	@Test
	public void purchase() {
		OrderWatchDTO orderWatchDto = new OrderWatchDTO();
		
		Mockito.when(watchService.purchaseWatch(orderWatchDto)).thenReturn(true);
		try {
			OrderWatchRequestModel orderWatchRequestModel = new OrderWatchRequestModel();
			orderWatchRequestModel.setWatch(1l);
			orderWatchRequestModel.setEmail("ayushi.138@gmail.com");
			orderWatchRequestModel.setAddress("abc");
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/watch/purchase",orderWatchRequestModel).accept(MediaType.APPLICATION_JSON);
			ResultActions actions = mockMvc.perform(requestBuilder);
			actions.andExpect(MockMvcResultMatchers.status().isOk());
			actions.andExpect(content().json("{\n" + 
					"    \"operationType\": \"REGISTER_USER\",\n" + 
					"    \"operationStatus\": \"SUCCESS\"\n" + 
					"}"));
		
		}
		catch(Exception e) {
			
		}
	}
	
}
