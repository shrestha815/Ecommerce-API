package com.example.model;

import lombok.Data;

@Data
public class CartItemResponse {
	private Long productId;
	private String productName;
	private Double price;
	private Integer quantity;
}
