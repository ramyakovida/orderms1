package com.infy.ordermanagement.OrderMS.controller;

import java.net.URI;
import java.util.ArrayList;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.infy.ordermanagement.OrderMS.dto.CartDTO;
import com.infy.ordermanagement.OrderMS.dto.ManageOrderDTO;
import com.infy.ordermanagement.OrderMS.dto.OrderDTO;
import com.infy.ordermanagement.OrderMS.dto.ProductDTO;
import com.infy.ordermanagement.OrderMS.dto.ProductOrderdDTO;
import com.infy.ordermanagement.OrderMS.exception.OrderMSException;
import com.infy.ordermanagement.OrderMS.service.OrderService;
import com.infy.ordermanagement.OrderMS.utility.CartDTOconverter;


@RestController
@RequestMapping(value = "/orderMS-api")
@EnableAutoConfiguration
public class OrderAPI {
	
	@Autowired 
	private OrderService orderService;
	
	@Autowired
	DiscoveryClient client;
	
	static Log logger = LogFactory.getLog(OrderAPI.class);
	
	@PostMapping(value = "/placeOrder/{buyerId}/{address}")
	public ResponseEntity<String> placeOrder(@PathVariable Integer buyerId, @PathVariable String address) throws OrderMSException{
		
		List<ServiceInstance> userInstances=client.getInstances("USERMS");
		ServiceInstance userInstance=userInstances.get(0);
    	URI userUri = userInstance.getUri();
    	
    	List<ServiceInstance> userIn=client.getInstances("PRODUCTMS");
		ServiceInstance userI=userIn.get(0);
    	URI productUri = userI.getUri();
		
    CartDTOconverter cartList=new RestTemplate().getForObject(userUri+"/userMS/buyer/cart/get/"+buyerId,CartDTOconverter.class);
    if(cartList==null||cartList.getCartDTOConverter().isEmpty()) {
    	return new ResponseEntity<>("No item in cart", HttpStatus.NOT_FOUND);
    }
		
    List<ProductDTO> prodDto=new ArrayList<>();
    for(CartDTO c:cartList.getCartDTOConverter()) {
    	prodDto.add(new RestTemplate().getForObject(productUri+"prodMS/getById/"+c.getProdId(), ProductDTO.class));
    }
    
    
    cartList.getCartDTOConverter().forEach(item->{
		new RestTemplate().getForObject(productUri+"/prodMS/updateStock/" +item.getProdId()+"/"+item.getQuantity(), boolean.class) ;
		new RestTemplate().postForObject(userUri+"/userMS/buyer/cart/remove/"+buyerId+"/"+item.getProdId(),null, String.class);
	});
   
    OrderDTO orderdto= orderService.placeOrder(cartList.getCartDTOConverter(), prodDto, address, buyerId);
    new RestTemplate().getForObject(userUri+"/userMS/updateRewardPoints/"+buyerId+"/"+orderdto.getRewardPoints() , String.class);
    
    String success = "The order is placed with order ID:"+orderdto.getOrderId() ;
		return new ResponseEntity<>(success, HttpStatus.OK);
	}
	
	@GetMapping(value = "/viewDealsPastOrder/{buyerId}")
	public ResponseEntity<List<OrderDTO>> viewPastOrders(@PathVariable("buyerId") Integer buyerId) throws OrderMSException {
		List<OrderDTO> orderList = orderService.viewPastOrders(buyerId);
		return new ResponseEntity<>(orderList, HttpStatus.OK);
	}
	
	@PostMapping(value = "/reorder/{buyerId}/{orderId}")
	public ResponseEntity<String> reorder(@PathVariable Integer buyerId,@PathVariable Integer orderId)
			throws OrderMSException {
		
		Integer order = orderService.reorder(buyerId, orderId);
		String success = "The order is placed with order ID:" + order;
		return new ResponseEntity<>(success, HttpStatus.OK);
	}
	
	@GetMapping(value = "/viewOrderBySeller/{sellerId}")
	public ResponseEntity<List<ProductOrderdDTO>> viewOrderBySeller(@PathVariable("sellerId") Integer sellerId) throws OrderMSException {
		
		List<ProductOrderdDTO> orderList = orderService.viewOrderbySeller(sellerId);
		return new ResponseEntity<>(orderList, HttpStatus.OK);
	}
	
	@PostMapping(value = "/manageOrder")
	public ResponseEntity<String> manageOrder(@RequestBody ManageOrderDTO mOrder)
			throws OrderMSException {
		String status = orderService.manageOrder(mOrder);
		String success = "The status of orderId: "+mOrder.getOrderId()+" is changed to: " + status ;
		return new ResponseEntity<>(success, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/orderMS/addToCart/{buyerId}/{prodId}/{quantity}")
	public ResponseEntity<String> addToCart(@PathVariable String buyerId, @PathVariable String prodId,@PathVariable Integer quantity) throws OrderMSException{		
		List<ServiceInstance> userInstances=client.getInstances("USERMS");
		ServiceInstance userInstance=userInstances.get(0);
    	URI userUri = userInstance.getUri();
			
			String successMsg = new RestTemplate().postForObject(userUri+"/userMS/buyer/cart/add/"+buyerId+"/"+prodId+"/"+quantity, null, String.class);

			return new ResponseEntity<>(successMsg,HttpStatus.ACCEPTED);
		
				
	}
	
	@PostMapping(value = "/orderMS/removeFromCart/{buyerId}/{prodId}")
	public ResponseEntity<String> removeFromCart(@PathVariable String buyerId, @PathVariable String prodId) throws OrderMSException{
			
		List<ServiceInstance> userInstances=client.getInstances("USERMS");
		ServiceInstance userInstance=userInstances.get(0);
    	URI userUri = userInstance.getUri();
    	
			String successMsg = new RestTemplate().postForObject(userUri+"/userMS/buyer/cart/remove/"+buyerId+"/"+prodId, null, String.class);
			
			return new ResponseEntity<>(successMsg,HttpStatus.ACCEPTED);
				
	}

}
