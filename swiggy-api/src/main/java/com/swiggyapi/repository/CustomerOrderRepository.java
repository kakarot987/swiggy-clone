package com.swiggyapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.swiggyapi.domain.Order;
import com.swiggyapi.domain.Order;

@Repository
public interface CustomerOrderRepository extends MongoRepository<Order,String> {
	
	
	@Query("{'customerInfo.userId':?0}")
	List<Order> findByUserId(String userId);
	
	
}
