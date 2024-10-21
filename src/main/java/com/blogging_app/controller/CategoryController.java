package com.blogging_app.controller;

import com.blogging_app.payload.ApiResponse;
import com.blogging_app.payload.CategoryDto;
import com.blogging_app.services.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/add/category")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
		return ResponseEntity.ok(categoryDto1);
	}

	@PutMapping("/update/category/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable int categoryId, @RequestBody CategoryDto categoryDto) {
		CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok().body(categoryDto1);
	}

	@GetMapping("/getcategory/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int categoryId) {
		return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
	}

	@GetMapping("/getallcategory")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		return ResponseEntity.ok().body(categoryService.getAllCategories());
	}

	@DeleteMapping("/deletecategory/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId) {
		categoryService.deleteCategoryById(categoryId);
		return ResponseEntity.ok().body(new ApiResponse(String.format("Category with id $d deleted successfully",categoryId), true));
	}
}
