package com.infy.ordermanagement.OrderMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.infy.ordermanagement.OrderMS.entity.ProductOrdered;
import com.infy.ordermanagement.OrderMS.utility.CompositeKey;

public interface ProductOrderedRepository extends CrudRepository<ProductOrdered, CompositeKey> {
	
	@Query("select p from ProductOrdered p where p.ProdOrdered.productId=?1")
	List<ProductOrdered> findProductOrdered(Integer productId);
	List<ProductOrdered> findBySellerId(Integer sellerId);
	@Query("select p from ProductOrdered p where p.sellerId=?1 and p.ProdOrdered.buyerId=?2")
	List<ProductOrdered> findPorductBySellerIdAndBuyerID(Integer sellerId,Integer buyerId);

}
