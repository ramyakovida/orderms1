package com.infy.ordermanagement.OrderMS.dto;

import java.time.LocalDate;

import com.infy.ordermanagement.OrderMS.entity.Order;

public class OrderDTO {
	
	private Integer orderId;
	
	private Integer buyerId;
	
	private Float amount;
	
	private LocalDate date;
	
	private String address;
	
	private String status;
	
	private Integer rewardPoints;
		
	private ProductOrderdDTO productOrderedDTO;
	
	
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public ProductOrderdDTO getProductOrderedDTO() {
		return productOrderedDTO;
	}
	public void setProductOrderedDTO(ProductOrderdDTO productOrderedDTO) {
		this.productOrderedDTO = productOrderedDTO;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Order createEntity() {
		
		Order order= new Order();
		order.setBuyerId(this.getBuyerId());
		order.setAmount(this.getAmount());
		order.setDate(this.getDate());
		order.setAddress(this.getAddress());
		order.setStatus(this.getStatus());
		return order;
	}
	
	public static OrderDTO toDTO(Order order) {
		
		OrderDTO orderDTO= new OrderDTO();
		orderDTO.setBuyerId(order.getBuyerId());
		orderDTO.setAmount(order.getAmount());
		orderDTO.setDate(order.getDate());
		orderDTO.setAddress(order.getAddress());
		orderDTO.setStatus(order.getStatus());
		orderDTO.setOrderId(order.getOrderId());
		return orderDTO;
		
	}
	

}
