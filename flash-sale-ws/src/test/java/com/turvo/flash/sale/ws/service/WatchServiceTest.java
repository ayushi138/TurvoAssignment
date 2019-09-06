package com.turvo.flash.sale.ws.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.turvo.flash.sale.ws.dto.OrderWatchDTO;
import com.turvo.flash.sale.ws.exceptions.WatchServiceException;
import com.turvo.flash.sale.ws.io.entity.CompanyUserEntity;
import com.turvo.flash.sale.ws.io.entity.OrderAddressDetailsEnitity;
import com.turvo.flash.sale.ws.io.entity.WatchEntity;
import com.turvo.flash.sale.ws.repository.CompanyUserRepository;
import com.turvo.flash.sale.ws.repository.OrderAddressDetailRepository;
import com.turvo.flash.sale.ws.repository.WatchRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchServiceTest {
		
	@Autowired
	private WatchService watchService;
	
	@MockBean
	WatchRepository watchRepository;
	
	@MockBean
	CompanyUserRepository companyUserRepository;
	
	@MockBean
	OrderAddressDetailRepository orderAddressDetailRepository;
	
	@Test(expected = WatchServiceException.class)
	public void testWatchPurchaseWhenIncorrectWatchIdGiven(){
		OrderWatchDTO watchDto = new OrderWatchDTO();
		watchDto.setAddress("xyz");
		watchDto.setEmail("xyz@xyz.com");
		watchDto.setWatch(1l);
		Mockito.when(watchRepository.findById(1l)).thenReturn(Optional.empty());
		
		watchService.purchaseWatch(watchDto);
	}
	
	@Test(expected = WatchServiceException.class)
	public void testWatchPurchaseWhenIncorrectEmailIdGiven(){
		OrderWatchDTO watchDto = new OrderWatchDTO();
		watchDto.setAddress("xyz");
		watchDto.setEmail("xyz@xyz.com");
		watchDto.setWatch(1l);
		
		WatchEntity watchEntity = new WatchEntity();
		watchEntity.setCost(1);
		watchEntity.setCount(2);
		watchEntity.setId(1l);
		watchEntity.setName("test");
		Optional<WatchEntity> watch= Optional.of(watchEntity);
		
		
		Mockito.when(watchRepository.findById(any(Long.class))).thenReturn(watch);
		Mockito.when(companyUserRepository.findByEmailAddress(any(String.class))).thenReturn(null);
		watchService.purchaseWatch(watchDto);
	}
	
	@Test(expected = Exception.class)
	public void testWatchPurchaseWhenCorrectValuesGivenButWatchIsSoldOut() {
		OrderWatchDTO watchDto = new OrderWatchDTO();
		watchDto.setAddress("xyz");
		watchDto.setEmail("xyz@xyz.com");
		watchDto.setWatch(1l);
		
		WatchEntity watchEntity = new WatchEntity();
		watchEntity.setCost(1);
		//setting the count to zero
		watchEntity.setCount(0);
		watchEntity.setId(1l);
		watchEntity.setName("test");
		Optional<WatchEntity> watch= Optional.of(watchEntity);
		CompanyUserEntity companyUserEntity = new CompanyUserEntity();
		companyUserEntity.setEmailAddress("xyz@xyz.com");
		companyUserEntity.setFirstName("xyz");
		companyUserEntity.setLastName("test");
		companyUserEntity.setPassword("xyz");
		Mockito.when(watchRepository.findById(any(Long.class))).thenReturn(watch);
		Mockito.when(companyUserRepository.findByEmailAddress(any(String.class))).thenReturn(companyUserEntity);
		watchService.purchaseWatch(watchDto);
	}
	
	@Test
	public void testWatchPurchaseWithCorrectData() {
		OrderWatchDTO watchDto = new OrderWatchDTO();
		watchDto.setAddress("xyz");
		watchDto.setEmail("xyz@xyz.com");
		watchDto.setWatch(1l);
		
		WatchEntity watchEntity = new WatchEntity();
		watchEntity.setCost(1);
		watchEntity.setCount(2);
		watchEntity.setId(1l);
		watchEntity.setName("test");
		Optional<WatchEntity> watch= Optional.of(watchEntity);
		
		CompanyUserEntity companyUserEntity = new CompanyUserEntity();
		companyUserEntity.setEmailAddress("xyz@xyz.com");
		companyUserEntity.setFirstName("xyz");
		companyUserEntity.setLastName("test");
		companyUserEntity.setPassword("xyz");
		
		OrderAddressDetailsEnitity orderAddressDetailsEnitity = new OrderAddressDetailsEnitity();
		
		Mockito.when(watchRepository.findById(any(Long.class))).thenReturn(watch);
		Mockito.when(companyUserRepository.findByEmailAddress(any(String.class))).thenReturn(companyUserEntity);
		Mockito.when(watchRepository.save(any(WatchEntity.class))).thenReturn(watchEntity);
		Mockito.when(orderAddressDetailRepository.save(any(OrderAddressDetailsEnitity.class))).thenReturn(orderAddressDetailsEnitity);
		
		assertTrue(watchService.purchaseWatch(watchDto));
	}
	
	
}
