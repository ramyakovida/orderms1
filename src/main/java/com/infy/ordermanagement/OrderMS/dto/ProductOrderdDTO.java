package com.infy.ordermanagement.OrderMS.dto;

import javax.persistence.EmbeddedId;

import com.infy.ordermanagement.OrderMS.entity.ProductOrdered;
import com.infy.ordermanagement.OrderMS.utility.CompositeKey;

public class ProductOrderdDTO {
	
	@EmbeddedId
    private CompositeKey compositeKey;
	
	private Integer sellerId;
	
	private Integer quantity;

	

	public CompositeKey getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(CompositeKey compositeKey) {
		this.compositeKey = compositeKey;
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
	
	public ProductOrdered toEntity() {
		
		ProductOrdered prod=new ProductOrdered();
		prod.setQuantity(this.getQuantity());
		prod.setSellerId(this.getSellerId());
		prod.setProdOrdered(this.getCompositeKey());
		return prod;
	}
	
	public static ProductOrderdDTO prodDTO(ProductOrdered pro) {
		
		ProductOrderdDTO prod =new ProductOrderdDTO();
		prod.setCompositeKey(pro.getProdOrdered());
		prod.setQuantity(pro.getQuantity());
		prod.setSellerId(pro.getSellerId());
		return prod;
	}

}
