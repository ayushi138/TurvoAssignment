package com.turvo.flash.sale.ws.dto;

import java.io.Serializable;

public class WatchDTO implements Serializable{
	
	private static final long serialVersionUID = -2916713737327231449L;
	private Long id;
	private String name;
	private int count;
	private int cost;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
}
