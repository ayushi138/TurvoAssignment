package com.turvo.flash.sale.ws.dto;

import java.io.Serializable;

public class RegisterCompanyUserDTO implements Serializable{

	private static final long serialVersionUID = -8239310800722998459L;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	private String user;
	private String password;
}
