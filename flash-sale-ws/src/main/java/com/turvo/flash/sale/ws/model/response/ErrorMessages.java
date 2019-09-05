package com.turvo.flash.sale.ws.model.response;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing required field.Please check documentation for required fields"),
	RECORD_ALREADY_EXIST("User Already Exists"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	AUTHENTICATION_FAILED("Authentication failed"),
	USER_NOT_REGISTERED("User not registered, Kindly register to proceed"),
	NO_USER_PRESENT("No user present! Add users first and then send email"),
	INVALID_USER("Invalid Email Id entered! Enter correct mail id"),
	INVALID_PASSWORD("Incorrect password entered, retry with correct password"),
	INVALID_COST("Cost of the watch cannot be 0 or negative"),
	INVALID_COUNT("Count of the watch cannot be 0 or negative"),
	NO_WATCH_PRESENT("No watches added!!"),
	INVALID_WATCH_ID("No watch with this id exists, kinldy check and retry with the correct one"),
	WATCHES_SOLD_OUT("All the watches are sold. Try it next time!");
	
	
	private String errorMessage;
	
	private ErrorMessages(String errorMessage) {
		this.errorMessage=errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
