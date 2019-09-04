package com.turvo.flash.sale.ws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.flash.sale.ws.io.entity.WatchEntity;

@Repository
public interface WatchRepository extends CrudRepository<WatchEntity, Long> {
	
}
