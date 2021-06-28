package com.infy.ordermanagement.OrderMS.dto;

public class ProductDTO {
	
	private Integer productId;
	
	private String prodName;
	
	private Float price;
	
	private Integer stock;
	
	private String description;
	
	private String image;
	
	private String category;
	
	private Integer sellerId;
	
	private String subCategory;
	
	private Float productRating;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public Float getProductRating() {
		return productRating;
	}

	public void setProductRating(Float productRating) {
		this.productRating = productRating;
	}
	
	

}
