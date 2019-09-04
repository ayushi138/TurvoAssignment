package com.turvo.flash.sale.ws.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.turvo.flash.sale.ws.io.entity.CompanyUserEntity;

@Repository
public interface CompanyUserRepository extends CrudRepository<CompanyUserEntity,Long>{
	
	CompanyUserEntity findByEmailAddress(String email);
	
}
