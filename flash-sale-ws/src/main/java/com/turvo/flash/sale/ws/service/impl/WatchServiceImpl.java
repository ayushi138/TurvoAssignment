package com.turvo.flash.sale.ws.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turvo.flash.sale.ws.dto.OrderWatchDTO;
import com.turvo.flash.sale.ws.dto.WatchDTO;
import com.turvo.flash.sale.ws.exceptions.WatchServiceException;
import com.turvo.flash.sale.ws.io.entity.CompanyUserEntity;
import com.turvo.flash.sale.ws.io.entity.OrderAddressDetailsEnitity;
import com.turvo.flash.sale.ws.io.entity.WatchEntity;
import com.turvo.flash.sale.ws.model.response.ErrorMessages;
import com.turvo.flash.sale.ws.repository.CompanyUserRepository;
import com.turvo.flash.sale.ws.repository.OrderAddressDetailRepository;
import com.turvo.flash.sale.ws.repository.WatchRepository;
import com.turvo.flash.sale.ws.service.WatchService;
import com.turvo.flash.sale.ws.shared.SingleLockObject;

@Service
public class WatchServiceImpl implements WatchService {
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(2);
	
	@Autowired
	WatchRepository watchRepository;
	
	@Autowired
	SingleLockObject singleLockObject;
	
	@Autowired
	CompanyUserRepository companyUserRepository;
	
	@Autowired
	OrderAddressDetailRepository orderAddressDetailRepository;
	
	@Override
	public WatchDTO createWatch(WatchDTO watchDto) {
	
		WatchEntity watchEntity = new WatchEntity();
		if(StringUtils.isBlank(watchDto.getName())) {
			throw new WatchServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}
		if(watchDto.getCost()==0) {
			throw new WatchServiceException(ErrorMessages.INVALID_COST.getErrorMessage());
		}
		if(watchDto.getCount()==0) {
			throw new WatchServiceException(ErrorMessages.INVALID_COUNT.getErrorMessage());
		}
		BeanUtils.copyProperties(watchDto, watchEntity);
		watchEntity = watchRepository.save(watchEntity);
		BeanUtils.copyProperties(watchEntity, watchDto);
		return watchDto;
	}

	@Override
	public List<WatchDTO> getAllWatches() {
		Iterable<WatchEntity> watches = watchRepository.findAll();
		if(((Collection<WatchEntity>)watches).size() == 0) {
			throw new WatchServiceException(ErrorMessages.NO_WATCH_PRESENT.getErrorMessage());
		}
		List<WatchDTO> watchDtos = new ArrayList<WatchDTO>();
		for(WatchEntity watch:watches) {
			if(watch.getCount()!= 0) {
				WatchDTO watchDto = new WatchDTO();
				BeanUtils.copyProperties(watch, watchDto);
				watchDtos.add(watchDto);
			}
			
		}
		return watchDtos;
	}

	@Override
	public boolean  purchaseWatch(OrderWatchDTO orderWatchDto) {
		boolean wasAbleToPurchase = false;
		try {
		Optional<WatchEntity> watchExists = watchRepository.findById(orderWatchDto.getWatch());
			if(!watchExists.isPresent()) {
				throw new WatchServiceException(ErrorMessages.INVALID_WATCH_ID.getErrorMessage());
			}
		CompanyUserEntity companyUserEntity = companyUserRepository.findByEmailAddress(orderWatchDto.getEmail());
		if(companyUserEntity == null) {
			throw new WatchServiceException(ErrorMessages.INVALID_USER.getErrorMessage());
		}
		Future<Boolean> task = executorService.submit(new Callable<Boolean>() {

			@Override
			public Boolean call() throws WatchServiceException {
				boolean done=false;
				try {
					//locking to start the purchase
					singleLockObject.getLock().lock();
					
					Optional<WatchEntity> watch = watchRepository.findById(orderWatchDto.getWatch());
					if(watch.isPresent() && watch.get().getCount()<=0) {
						throw new WatchServiceException(ErrorMessages.WATCHES_SOLD_OUT.getErrorMessage());
					}
					if(watch.isPresent() && watch.get().getCount() > 0) {
						WatchEntity watchEntity = watch.get();
						watchEntity.setCount(watchEntity.getCount()-1);
						watchRepository.save(watchEntity);
						done= true;
					}
				}
				finally {
					singleLockObject.getLock().unlock();
				}
				
				return done;
				
			}
		});
		
		
		wasAbleToPurchase= task.get();
		if(wasAbleToPurchase) {
			WatchEntity watchEntity = watchRepository.findById(orderWatchDto.getWatch()).get();
			OrderAddressDetailsEnitity order = new OrderAddressDetailsEnitity();
			order.setAddress(StringUtils.isBlank(orderWatchDto.getAddress())?"Not provided":orderWatchDto.getAddress());
			order.setCompanyUserEntity(companyUserEntity);
			order.setWatchEntity(watchEntity);
			orderAddressDetailRepository.save(order);
		}
		}
		catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage());
		}
		return wasAbleToPurchase;
	}
	
}
