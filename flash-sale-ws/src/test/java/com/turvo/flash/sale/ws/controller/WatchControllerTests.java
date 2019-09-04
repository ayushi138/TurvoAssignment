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
import static org.mockito.ArgumentMatchers.any;


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
		
		Mockito.when(watchService.purchaseWatch(any(OrderWatchDTO.class))).thenReturn(true);
		try {
			OrderWatchRequestModel orderWatchRequestModel = new OrderWatchRequestModel();
			orderWatchRequestModel.setWatch(1l);
			orderWatchRequestModel.setEmail("xyz@xyz.com");
			orderWatchRequestModel.setAddress("abc");
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/watch/purchase",orderWatchRequestModel).accept(MediaType.APPLICATION_JSON).content(CompanyUserControllerTests.asJsonString(orderWatchRequestModel));
			ResultActions actions = mockMvc.perform(requestBuilder);
			actions.andExpect(MockMvcResultMatchers.status().isOk());
			actions.andExpect(content().json("{\n" + 
					"    \"operationType\": \"PURCHASE_WATCH\",\n" + 
					"    \"operationStatus\": \"SUCCESS\"\n" + 
					"}"));
		
		}
		catch(Exception e) {
			
		}
	}
	
}
