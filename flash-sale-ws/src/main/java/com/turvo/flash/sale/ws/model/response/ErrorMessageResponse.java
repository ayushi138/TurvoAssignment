package com.turvo.flash.sale.ws.model.response;

import java.util.Date;

public class ErrorMessageResponse {
	
	private Date date;
	private String error;
	
	public ErrorMessageResponse(Date date,String error) {
		this.date=date;
		this.error=error;
	}
	
	public ErrorMessageResponse() {
		
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
