package com.turvo.flash.sale.ws.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "order_detail")
public class OrderAddressDetailsEnitity implements Serializable{

	private static final long serialVersionUID = 7752878972112017076L;
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private CompanyUserEntity companyUserEntity;
	
	@ManyToOne
	private WatchEntity watchEntity;
	
	@Column
	private String address;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CompanyUserEntity getCompanyUserEntity() {
		return companyUserEntity;
	}
	public void setCompanyUserEntity(CompanyUserEntity companyUserEntity) {
		this.companyUserEntity = companyUserEntity;
	}
	public WatchEntity getWatchEntity() {
		return watchEntity;
	}
	public void setWatchEntity(WatchEntity watchEntity) {
		this.watchEntity = watchEntity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

}
