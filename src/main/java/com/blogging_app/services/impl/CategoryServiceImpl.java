package com.blogging_app.services.impl;

import com.blogging_app.entity.Category;
import com.blogging_app.exceptions.ResourceNotFoundException;
import com.blogging_app.payload.CategoryDto;
import com.blogging_app.repository.CategoryRepo;
import com.blogging_app.services.CategoryService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto category) {
		Category categoryEntity = modelMapper.map(category, Category.class);
		Category savedCategory = categoryRepo.save(categoryEntity);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		Category category = categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", id));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category savedCategory = categoryRepo.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> categoryDtos = new ArrayList<>();
		for (Category category : categories) {categoryDtos.add(modelMapper.map(category, CategoryDto.class));}
		return categoryDtos;
	}

	@Override
	public CategoryDto getCategoryById(int id) {
		Category category = categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", id));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategoryById(int id) {
		Category category = categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category", id));
		categoryRepo.delete(category);
	}
}
