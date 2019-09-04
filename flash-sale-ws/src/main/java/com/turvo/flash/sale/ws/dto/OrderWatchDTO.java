package com.turvo.flash.sale.ws.dto;

import java.io.Serializable;

public class OrderWatchDTO implements Serializable{

	private static final long serialVersionUID = 8265240922006864573L;
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
