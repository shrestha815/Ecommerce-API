package com.example.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.entity.Category;
import com.example.entity.Product;
import com.example.model.ProductRequest;
import com.example.model.ProductResponse;
import com.example.repo.CategoryRepository;
import com.example.repo.ProductRepository;
import com.example.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

	@Override
	public ProductResponse createProduct(ProductRequest request) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not found"));
		
		Product product = Product.builder()
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.stockQuantity(request.getStockQuantity())
				.category(category)
				.build();
		product = productRepository.save(product);
		return mapToResponse(product);
	}

	@Override
	public ProductResponse updateProduct(Long productId, ProductRequest request) {
		// TODO Auto-generated method stub
		//find existing product and category then use setters
		Product product = productRepository.findById(productId)
				.orElseThrow(()-> new RuntimeException("Product not found"));
		
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(()-> new RuntimeException("Category not found"));
		
		product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);
		return mapToResponse(productRepository.save(product));
	}

	@Override
	public void deleteProduct(Long productId) {
		// TODO Auto-generated method stub
		productRepository.deleteById(productId);

	}

	@Override
	public List<ProductResponse> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public ProductResponse getProductById(Long productId) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId)
				.orElseThrow(()-> new RuntimeException("Product not found"));
		return mapToResponse(product);
	}
	
	private ProductResponse mapToResponse(Product product) {
		// TODO Auto-generated method stub
		ProductResponse response = new ProductResponse();
		response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategoryName(product.getCategory().getName());
        return response;
	}

}
