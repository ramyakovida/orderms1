package com.infy.ordermanagement.OrderMS.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.ordermanagement.OrderMS.dto.CartDTO;
import com.infy.ordermanagement.OrderMS.dto.ManageOrderDTO;
import com.infy.ordermanagement.OrderMS.dto.OrderDTO;
import com.infy.ordermanagement.OrderMS.dto.ProductDTO;
import com.infy.ordermanagement.OrderMS.dto.ProductOrderdDTO;
import com.infy.ordermanagement.OrderMS.entity.Order;
import com.infy.ordermanagement.OrderMS.entity.ProductOrdered;
import com.infy.ordermanagement.OrderMS.exception.OrderMSException;
import com.infy.ordermanagement.OrderMS.repository.OrderRepository;
import com.infy.ordermanagement.OrderMS.repository.ProductOrderedRepository;
import com.infy.ordermanagement.OrderMS.utility.CompositeKey;
import com.infy.ordermanagement.OrderMS.utility.OrderStatus;



@Service(value="OrderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductOrderedRepository productOrderedRepository;

	@Override
	public OrderDTO placeOrder(List<CartDTO> cartDTO, List<ProductDTO> prodDTO, String address,Integer buyerId) {
		
		Float sum=0f;
		List<ProductOrdered> prodOrdered= new ArrayList<>();
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setBuyerId(buyerId);
		orderDTO.setDate(LocalDate.now());
		orderDTO.setAddress(address);
		orderDTO.setStatus(OrderStatus.ORDER_PLACED.toString());
		for(int i=0;i<cartDTO.size();i++) {
			sum+=cartDTO.get(i).getQuantity()*prodDTO.get(i).getPrice();
			
			ProductOrderdDTO prod = new ProductOrderdDTO();
			prod.setSellerId(prodDTO.get(i).getSellerId());
			prod.setCompositeKey(new CompositeKey(cartDTO.get(i).getBuyerId(), cartDTO.get(i).getProdId()) );
			prod.setQuantity(cartDTO.get(i).getQuantity());
			ProductOrdered pro=prod.toEntity();
			prodOrdered.add(pro);
		}
		productOrderedRepository.saveAll(prodOrdered);
		orderDTO.setAmount(sum);
		Order order=orderDTO.createEntity();
		
		Integer rewardPts = (int) (order.getAmount()/100);		
		orderDTO.setRewardPoints(rewardPts);
		orderDTO.setOrderId(orderRepository.save(order).getOrderId());
		
		return orderDTO;
	}

	@Override
	public List<OrderDTO> viewPastOrders(Integer buyerId) throws OrderMSException {
		List<Order> order=orderRepository.findByBuyerId(buyerId);
		if(order==null||order.isEmpty()) {
			throw new OrderMSException("OrderMSService.ORDER_NOT_FOUND");
		}
		List<OrderDTO> orderDTO=new ArrayList<>();
		for(Order o:order) {
			OrderDTO ord=OrderDTO.toDTO(o);
			orderDTO.add(ord);
		}
		return orderDTO;
	}

	@Override
	public Integer reorder(Integer buyerId, Integer orderId) throws OrderMSException {
		
		
		List<Order> order=orderRepository.findByBuyerId(buyerId);
		if(order==null||order.isEmpty()) {
			throw new OrderMSException("OrderMSService.Buyer_NOT_FOUND");
		}
		
		Order o = new Order();
		for(Order a:order) {
			if(a.getOrderId().equals(orderId)) {
				
				o.setBuyerId(buyerId);
				o.setAddress(a.getAddress());
				o.setAmount(a.getAmount());
				o.setDate(LocalDate.now());
				o.setStatus(OrderStatus.ORDER_PLACED.toString());
			}
		}
		
		if(o.getBuyerId()==null||o.getBuyerId()==0) {
			throw new OrderMSException("OrderMSService.Buyer_NOT_MADE_ORDER");
		}
		
		return orderRepository.save(o).getOrderId();
	}

	@Override
	public List<ProductOrderdDTO> viewOrderbySeller(Integer sellerId) throws OrderMSException {
		List<ProductOrdered> prodOrdered=productOrderedRepository.findBySellerId(sellerId);
		if(prodOrdered==null||prodOrdered.isEmpty()) {
			throw new OrderMSException("OrderMSService.PRODUCT_NOT_FOUND");
		}
		List<ProductOrderdDTO> prodDTO=new ArrayList<>();
		for(ProductOrdered p:prodOrdered) {
			prodDTO.add(ProductOrderdDTO.prodDTO(p));
		}
		return prodDTO;
	}

	@Override
	public String manageOrder(ManageOrderDTO mOrder) throws OrderMSException {
		List<ProductOrdered> pro=productOrderedRepository.findPorductBySellerIdAndBuyerID(mOrder.getSellerId(), mOrder.getBuyerId());
		if(pro==null||pro.isEmpty()){
			throw new OrderMSException("OrderMSService.PRODUCT_NOT_FOUND"); 
		}
		
		Optional<Order> optional=orderRepository.findByOrderIdAndBuyerId(mOrder.getOrderId(), mOrder.getBuyerId());
		Order order=optional.orElseThrow(() -> new OrderMSException("OrderMSService.ORDER_NOT_FOUND"));
		
		
		
		order.setStatus(mOrder.getStatus());


		return mOrder.getStatus();
	}
	
	
	
	

}
