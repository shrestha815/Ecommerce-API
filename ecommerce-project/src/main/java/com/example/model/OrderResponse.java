package com.example.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {
	private Long orderId;
	private Double totalAmount;
	private String status;
	private LocalDateTime orderDate;
	private List<OrderItemResponse> item;
	private PaymentResponse payment;
}
