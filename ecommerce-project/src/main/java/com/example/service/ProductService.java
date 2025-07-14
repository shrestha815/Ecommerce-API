package com.example.service;

import com.example.model.ProductRequest;
import com.example.model.ProductResponse;
import java.util.List;

public interface ProductService {
	ProductResponse createProduct(ProductRequest request);
	ProductResponse updateProduct(Long productId, ProductRequest request);
	void deleteProduct(Long productId);
	List<ProductResponse> getAllProducts();
	ProductResponse getProductById(Long productId);
}
