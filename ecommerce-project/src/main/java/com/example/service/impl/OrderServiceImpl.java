package com.example.service.impl;

import com.example.entity.*;

import com.example.model.OrderItemResponse;
import com.example.model.OrderResponse;
import com.example.model.PaymentResponse;
import com.example.repo.*;
import com.example.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

	private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

	@Override
	public OrderResponse placeOrder(Long userId, String paymentMethod) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findByUserId(userId)
				.orElseThrow(()-> new RuntimeException("Cart not found"));
		if(cart.getItems().isEmpty()) throw new RuntimeException("Your cart is empty");
		
		double total = cart.getItems().stream()
				.mapToDouble(i->i.getProduct().getPrice()* i.getQuantity())
				.sum();
		
		Order order = new Order();
		order.setUser(cart.getUser());
		//review
		order.setCreatedAt(LocalDateTime.now());
		order.setStatus("PLACED");
		
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cart.getItems()) {
		    OrderItem item = new OrderItem();
		    item.setOrder(order);
		    item.setProduct(cartItem.getProduct());
		    item.setQuantity(cartItem.getQuantity());
		    item.setPrice(cartItem.getProduct().getPrice());
		    orderItems.add(item);
		}
		order.setItems(orderItems);
        order.setTotalAmount(total);
        order = orderRepository.save(order);
        
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(total);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("PAID");
        payment = paymentRepository.save(payment);
        
        cart.getItems().clear();
        cartRepository.save(cart);
        
		return mapToResponse(order,payment);
	}

	private OrderResponse mapToResponse(Order order, Payment payment) {
		// TODO Auto-generated method stub
		OrderResponse response = new OrderResponse();
		response.setOrderId(order.getId());
		response.setOrderDate(order.getCreatedAt());
		response.setTotalAmount(order.getTotalAmount());
		response.setStatus(order.getStatus());
		
		List<OrderItemResponse> items = order.getItems().stream().map(item -> {
			OrderItemResponse itemResponse = new OrderItemResponse();
			itemResponse.setProductId(item.getProduct().getId());
			itemResponse.setProductName(item.getProduct().getName());
			itemResponse.setQuantity(item.getQuantity());
			itemResponse.setPrice(item.getPrice());
			return itemResponse;
		}).collect(Collectors.toList());
		response.setItem(items);
		
		if(payment != null) {
			PaymentResponse paymentResponse = new PaymentResponse();
			paymentResponse.setPaymentId(payment.getId());
			paymentResponse.setAmount(payment.getAmount());
			paymentResponse.setStatus(payment.getStatus());
			paymentResponse.setPaymentMethod(payment.getPaymentMethod());
			response.setPayment(paymentResponse);
		}
		
		return response;
	}

	@Override
	public List<OrderResponse> getOrderByUserId(Long userId) {
		// TODO Auto-generated method stub
		return orderRepository.findByUserId(userId)
				.stream()
				.map(order -> {
					Payment payment = order.getPayment();
					return mapToResponse(order, payment);
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<OrderResponse> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAll()
				.stream()
				.map(order -> mapToResponse(order, order.getPayment()))
				.collect(Collectors.toList());
	}

}
