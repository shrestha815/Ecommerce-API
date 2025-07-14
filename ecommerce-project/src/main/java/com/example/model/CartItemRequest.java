package com.example.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {
	
	@NotNull(message = "Product Id is required")
	private Long ProductId;
	
	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;

}
