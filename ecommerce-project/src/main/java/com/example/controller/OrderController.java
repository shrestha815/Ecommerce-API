package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.OrderResponse;
import com.example.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping("/place")
	public ResponseEntity<OrderResponse> placeOrder (@RequestParam Long userId, @RequestParam String paymentMethod){
		OrderResponse response = orderService.placeOrder(userId, paymentMethod);
		return ResponseEntity.ok(response);
	}
	
	//specific order through user id
	@GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok(orders);
    }
	
	//all orders
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getAllOrders(){
		List<OrderResponse> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}

}
