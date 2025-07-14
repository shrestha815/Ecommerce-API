package com.example.service;

import com.example.model.CategoryRequest;
import com.example.model.CategoryResponse;

import java.util.List;

public interface CategoryService {
	CategoryResponse createCategory(CategoryRequest request);
	CategoryResponse updateCategory(Long id, CategoryRequest request);
	void deleteCategory(Long id);
	List<CategoryResponse> getAllCategories();
	CategoryResponse getCategoryById(Long id);
}
