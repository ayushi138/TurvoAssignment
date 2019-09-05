package com.turvo.flash.sale.ws.exceptions;

public class WatchServiceException extends RuntimeException{

	private static final long serialVersionUID = -2112829472237802245L;
	
	public WatchServiceException(String errorMessage) {
		super(errorMessage);
	}

}
