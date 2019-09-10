package com.turvo.flash.sale.ws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turvo.flash.sale.ws.dto.OrderWatchDTO;
import com.turvo.flash.sale.ws.dto.WatchDTO;
import com.turvo.flash.sale.ws.model.request.OrderWatchRequestModel;
import com.turvo.flash.sale.ws.model.request.WatchRequestModel;
import com.turvo.flash.sale.ws.model.response.OperationResponse;
import com.turvo.flash.sale.ws.model.response.OperationStatus;
import com.turvo.flash.sale.ws.model.response.OperationType;
import com.turvo.flash.sale.ws.model.response.WatchResponse;
import com.turvo.flash.sale.ws.service.WatchService;

/*
 * Exposes end point URLs for the entity Watch
 */

@RestController()
@RequestMapping("/watches")
public class WatchController {
	
	@Autowired
	WatchService watchService;
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public WatchResponse addWatch(@RequestBody WatchRequestModel watchRequestModel) {
		
		WatchDTO watchDto = new WatchDTO();
		
		BeanUtils.copyProperties(watchRequestModel, watchDto);
		
		watchDto = watchService.createWatch(watchDto);
		
		WatchResponse watchResponse = new WatchResponse();
		BeanUtils.copyProperties(watchDto, watchResponse);
		
		return watchResponse;
		
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<WatchResponse> getAllWatches() {
		
		
		List<WatchDTO> watchDtos = watchService.getAllWatches();
		
		List<WatchResponse> watchResponses = new ArrayList<WatchResponse>();
		
		for(WatchDTO watchDTO:watchDtos) {
			WatchResponse watchResponse = new WatchResponse();
			
			BeanUtils.copyProperties(watchDTO, watchResponse);
			watchResponses.add(watchResponse);
		}
		
		return watchResponses;
		
	}
	
	@PostMapping(path="/purchase" , consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public OperationResponse purchaseWatch(@RequestBody OrderWatchRequestModel orderWatchRequestModel ) {
		
		OrderWatchDTO orderWatchDto = new OrderWatchDTO();
		
		BeanUtils.copyProperties(orderWatchRequestModel, orderWatchDto);
		
		OperationResponse operationResponse = new OperationResponse();
		operationResponse.setOperationType(OperationType.PURCHASE_WATCH.name());

			boolean purchaseSuccessful = watchService.purchaseWatch(orderWatchDto);
			if(purchaseSuccessful) {
				operationResponse.setOperationStatus(OperationStatus.SUCCESS.name());
			}
			else
				operationResponse.setOperationStatus(OperationStatus.ERROR.name());
	
		
		return operationResponse;
		
	}

}
