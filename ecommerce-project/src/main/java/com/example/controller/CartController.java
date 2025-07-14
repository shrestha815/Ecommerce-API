package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.CartItemRequest;
import com.example.model.CartResponse;
import com.example.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	@PostMapping("/add")
	public ResponseEntity<CartResponse> addToCart(@RequestParam Long userId, @Valid @RequestBody CartItemRequest request) {
		cartService.addItemToCart(userId, request.getProductId(), request.getQuantity());
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<Void> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
		cartService.removeItemFromCart(userId, productId);
		return ResponseEntity.noContent().build();
	}
	
	//clear cart
	@DeleteMapping("/clear/{userId}")
	public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
		cartService.clearCart(userId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
		return ResponseEntity.ok(cartService.getCartByUserId(userId));
	}

}
