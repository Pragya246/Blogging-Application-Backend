package com.blogging_app.controller;

import com.blogging_app.config.AppConstants;
import com.blogging_app.payload.ApiResponse;
import com.blogging_app.payload.PostDto;
import com.blogging_app.payload.PostResponse;
import com.blogging_app.services.CategoryService;
import com.blogging_app.services.PostService;
import com.blogging_app.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postService;


	@PostMapping("/addpost/{userId}/{categoryId}")
	ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
		PostDto postDto1 = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
	}

	@PutMapping("/updatepost/{postId}")
	ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto postDto1 = postService.updatePost(postDto, postId);
		return ResponseEntity.ok(postDto1);
	}

	@GetMapping("/getpost/{postId}")
	ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {
		PostDto postDto = postService.getpostById(postId);
		return ResponseEntity.ok(postDto);
	}
	@GetMapping("/getallpost")
	ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
		@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
		@RequestParam(value = "sortBy", defaultValue = "addedDate", required = false) String sortBy) {

		PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy);
		return ResponseEntity.ok(postResponse);
	}
	@GetMapping("/getallpostbyuser/{userId}")
	ResponseEntity<PostResponse> getAllPostByUser(@PathVariable Integer userId,
		@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
		@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
		@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {

		PostResponse postResponse = postService.getAllPostByUser(userId, pageNo, pageSize, sortBy);
		return ResponseEntity.ok(postResponse);
	}
	@GetMapping("/getallpostbycategory/{categoryId}")
	ResponseEntity<PostResponse> getAllPostByCategory(@PathVariable Integer categoryId,
		@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
		@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
		@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {

		PostResponse postResponse = postService.getAllPostByCategory(categoryId, pageNo, pageSize, sortBy);
		return ResponseEntity.ok(postResponse);
	}
	@DeleteMapping("/deletepost/{postId}")
	ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse(String.format("Post with id %d deleted successfully", postId), true));
	}

	@GetMapping("/searchpost/{keyword}")
	ResponseEntity<PostResponse> searchPost(@PathVariable String keyword,
		@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
		@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
		@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
		@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		PostResponse postResponse = postService.searchPost(keyword, pageNo, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(postResponse);
	}
}
