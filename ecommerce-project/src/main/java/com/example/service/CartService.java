package com.example.service;

import com.example.model.CartResponse;

public interface CartService {
	CartResponse getCartByUserId(Long userId);
	void addItemToCart(Long userId, Long productId, int quantity);
	void removeItemFromCart(Long userId, Long productId);
	void clearCart(Long userId);
}
