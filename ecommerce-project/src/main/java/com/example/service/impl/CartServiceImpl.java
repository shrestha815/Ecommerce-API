package com.example.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.entity.Cart;
import com.example.entity.CartItem;
import com.example.entity.Product;
import com.example.entity.User;
import com.example.model.CartItemResponse;
import com.example.model.CartResponse;
import com.example.repo.CartRepository;
import com.example.repo.ProductRepository;
import com.example.repo.UserRepository;
import com.example.service.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
	
	private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

	@Override
	public CartResponse getCartByUserId(Long userId) {
		// TODO Auto-generated method stub
		Cart cart = getOrCreateCart(userId);
		return mapToResponse(cart);
	}

	private CartResponse mapToResponse(Cart cart) {
		// TODO Auto-generated method stub
		List<CartItemResponse> items = cart.getItems().stream().map(item -> {
			CartItemResponse response = new CartItemResponse();
			response.setProductId(item.getProduct().getId());
			response.setProductName(item.getProduct().getName());
			response.setPrice(item.getProduct().getPrice());
			response.setQuantity(item.getQuantity());
			return response;
		}).collect(Collectors.toList());
		
		double total =  items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
		CartResponse response = new CartResponse();
	    response.setCartId(cart.getId());
	    response.setItems(items);
	    response.setTotalAmount(total);
		return response;
	}

	private Cart getOrCreateCart(Long userId) {
		// TODO Auto-generated method stub
		//review this 
		return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
	}

	@Override
	public void addItemToCart(Long userId, Long productId, int quantity) {
		// TODO Auto-generated method stub
		Cart cart = getOrCreateCart(userId);
		Product product = productRepository.findById(productId)
				.orElseThrow(()-> new RuntimeException("Product not found"));
		
		Optional<CartItem> existingItem = cart.getItems().stream()
				.filter(i -> i.getProduct().getId().equals(productId))
				.findFirst();
		
		if(existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setQuantity(item.getQuantity() + quantity);
		} else {
			CartItem newItem = new CartItem();
			newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
		}
		cartRepository.save(cart);
	}

	@Override
	public void removeItemFromCart(Long userId, Long productId) {
		// TODO Auto-generated method stub
		Cart cart = getOrCreateCart(userId);
		cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
		cartRepository.save(cart);
	}

	@Override
	public void clearCart(Long userId) {
		// TODO Auto-generated method stub
		Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);

	}

}
