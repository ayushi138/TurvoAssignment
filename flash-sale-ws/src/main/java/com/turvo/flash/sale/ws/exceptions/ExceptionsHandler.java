package com.turvo.flash.sale.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.turvo.flash.sale.ws.model.response.ErrorMessageResponse;
import com.turvo.flash.sale.ws.model.response.ErrorMessages;

@ControllerAdvice
public class ExceptionsHandler {
	
	@ExceptionHandler(value = {CompanyUserServiceException.class})
	public ResponseEntity<Object> handleCompanyUserServiceException(CompanyUserServiceException ex, WebRequest request){
		
		ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(new Date(), ex.getMessage());
		
		if(ex.getMessage().equals(ErrorMessages.NO_USER_PRESENT.getErrorMessage())) {
			
			return new ResponseEntity<>(errorMessageResponse,new HttpHeaders(),HttpStatus.OK);
		}
		
		return new ResponseEntity<>(errorMessageResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = {WatchServiceException.class})
	public ResponseEntity<Object> handleWatchServiceException(WatchServiceException ex, WebRequest request){
		
		ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(new Date(), ex.getMessage());
		
		if(ex.getMessage().equals(ErrorMessages.NO_WATCH_PRESENT.getErrorMessage())) {
			
			return new ResponseEntity<>(errorMessageResponse,new HttpHeaders(),HttpStatus.OK);
		}
		
		
		return new ResponseEntity<>(errorMessageResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAllOtherException(Exception ex, WebRequest request){
		
		ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(new Date(), ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
		
		return new ResponseEntity<>(errorMessageResponse,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
}
