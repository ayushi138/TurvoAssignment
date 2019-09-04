package com.turvo.flash.sale.ws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.flash.sale.ws.io.entity.OrderAddressDetailsEnitity;

@Repository
public interface OrderAddressDetailRepository extends CrudRepository<OrderAddressDetailsEnitity, Long> {

}
