package com.turvo.flash.sale.ws.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turvo.flash.sale.ws.dto.OrderWatchDTO;
import com.turvo.flash.sale.ws.dto.WatchDTO;
import com.turvo.flash.sale.ws.io.entity.CompanyUserEntity;
import com.turvo.flash.sale.ws.io.entity.OrderAddressDetailsEnitity;
import com.turvo.flash.sale.ws.io.entity.WatchEntity;
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
		BeanUtils.copyProperties(watchDto, watchEntity);
		watchEntity = watchRepository.save(watchEntity);
		BeanUtils.copyProperties(watchEntity, watchDto);
		return watchDto;
	}

	@Override
	public List<WatchDTO> getAllWatches() {
		Iterable<WatchEntity> watches = watchRepository.findAll();
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
		Future<Boolean> task = executorService.submit(new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				boolean done=false;
				//locking to start the purchase
				singleLockObject.getLock().lock();
				
				Optional<WatchEntity> watch = watchRepository.findById(orderWatchDto.getWatch());
				if(watch.isPresent() && watch.get().getCount() > 0) {
					WatchEntity watchEntity = watch.get();
					watchEntity.setCount(watchEntity.getCount()-1);
					watchRepository.save(watchEntity);
					done= true;

				}
				singleLockObject.getLock().unlock();
				return done;
				
			}
		});
		
		
		wasAbleToPurchase= task.get();
		if(wasAbleToPurchase) {
			WatchEntity watchEntity = watchRepository.findById(orderWatchDto.getWatch()).get();
			CompanyUserEntity companyUserEntity = companyUserRepository.findByEmailAddress(orderWatchDto.getEmail());
			OrderAddressDetailsEnitity order = new OrderAddressDetailsEnitity();
			order.setAddress(orderWatchDto.getAddress());
			order.setCompanyUserEntity(companyUserEntity);
			order.setWatchEntity(watchEntity);
			orderAddressDetailRepository.save(order);
		}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wasAbleToPurchase;
	}
	
}
