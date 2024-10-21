package com.blogging_app.services;


import com.blogging_app.payload.CategoryDto;
import java.util.List;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto category);
	CategoryDto updateCategory(CategoryDto category, Integer id);
	List<CategoryDto> getAllCategories();
	CategoryDto getCategoryById(int id);
	void deleteCategoryById(int id);
}
