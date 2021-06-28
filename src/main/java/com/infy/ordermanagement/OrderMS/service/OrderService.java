package com.infy.ordermanagement.OrderMS.service;

import java.util.List;

import com.infy.ordermanagement.OrderMS.dto.CartDTO;
import com.infy.ordermanagement.OrderMS.dto.ManageOrderDTO;
import com.infy.ordermanagement.OrderMS.dto.OrderDTO;
import com.infy.ordermanagement.OrderMS.dto.ProductDTO;
import com.infy.ordermanagement.OrderMS.dto.ProductOrderdDTO;

import com.infy.ordermanagement.OrderMS.exception.OrderMSException;

public interface OrderService {
	
	OrderDTO  placeOrder(List<CartDTO> cartDTO,List<ProductDTO> prodDTO,String address,Integer buyerId);
	List<OrderDTO> viewPastOrders(Integer buyerId) throws OrderMSException;
	Integer reorder(Integer buyerId,Integer orderId) throws OrderMSException;
	List<ProductOrderdDTO> viewOrderbySeller(Integer sellerId) throws OrderMSException;
	String manageOrder(ManageOrderDTO mOrder) throws OrderMSException;

}
