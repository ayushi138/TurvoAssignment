package com.turvo.flash.sale.ws.service;

import java.util.List;

import com.turvo.flash.sale.ws.dto.OrderWatchDTO;
import com.turvo.flash.sale.ws.dto.WatchDTO;

public interface WatchService {

	WatchDTO createWatch(WatchDTO watchDto);

	List<WatchDTO> getAllWatches();

	boolean purchaseWatch(OrderWatchDTO orderWatchDto);
	

}
