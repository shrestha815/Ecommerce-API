package com.example.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.entity.Category;
import com.example.model.CategoryRequest;
import com.example.model.CategoryResponse;
import com.example.repo.CategoryRepository;
import com.example.service.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	@Override
	public CategoryResponse createCategory(CategoryRequest request) {
		// TODO Auto-generated method stub
		Category category = Category.builder()
				.name(request.getName())
				.description(request.getDescription())
				.build();
		return mapToResponse(categoryRepository.save(category));
	}

	@Override
	public CategoryResponse updateCategory(Long id, CategoryRequest request) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Category not found"));
		
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		
		return mapToResponse(categoryRepository.save(category));
	}

	@Override
	public void deleteCategory(Long id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);

	}

	@Override
	public List<CategoryResponse> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryResponse getCategoryById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.findById(id)
				.map(this::mapToResponse)
				.orElseThrow(()-> new RuntimeException("Category not found"));
	}
	
	private CategoryResponse mapToResponse(Category category) {
		CategoryResponse response = new CategoryResponse();
		response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
	}

}
