package com.infy.ordermanagement.OrderMS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.infy.ordermanagement.OrderMS.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	
	List<Order> findByBuyerId(Integer buyerId);
	Optional<Order> findByOrderIdAndBuyerId(Integer orderId, Integer buyerId);

}
