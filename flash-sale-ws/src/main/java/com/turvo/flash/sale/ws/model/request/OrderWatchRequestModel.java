package com.turvo.flash.sale.ws.model.request;

public class OrderWatchRequestModel {
	
	private String email;
	private Long watch;
	private String address;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getWatch() {
		return watch;
	}
	public void setWatch(Long watch) {
		this.watch = watch;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
