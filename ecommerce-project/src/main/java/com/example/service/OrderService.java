package com.example.service;

import com.example.model.OrderResponse;
import java.util.List;

public interface OrderService {
	OrderResponse placeOrder(Long userId, String paymentMethod);
	List<OrderResponse> getOrderByUserId(Long userId);
	List<OrderResponse> getAllOrders();
}
