package com.infy.ordermanagement.OrderMS.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infy.ordermanagement.OrderMS.utility.CompositeKey;

@Entity
@Table(name = "products_ordered")
public class ProductOrdered {
	
	@EmbeddedId
	public CompositeKey ProdOrdered;
	
	public Integer sellerId;
	
	public Integer quantity;

	public CompositeKey getProdOrdered() {
		return ProdOrdered;
	}

	public void setProdOrdered(CompositeKey prodOrdered) {
		ProdOrdered = prodOrdered;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

}
