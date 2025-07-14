package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ProductRequest;
import com.example.model.ProductResponse;
import com.example.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {
	
	private final ProductService productService;
	
	//Admin Actions: Create Products, Update them and Delete them
	@PostMapping
	public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
		return ResponseEntity.ok(productService.createProduct(request));
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductResponse> update(@PathVariable Long productId , @Valid @RequestBody ProductRequest request) {
		return ResponseEntity.ok(productService.updateProduct(productId, request));
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<ProductResponse> delete(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.noContent().build();
	}
	
	//Customer and Admin can view all products as well as view product via its given Id
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAll() {
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getById(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.getProductById(productId));
	}
	
}
