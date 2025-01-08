package com.blogging_app.controller;

import com.blogging_app.payload.ApiResponse;
import com.blogging_app.payload.PostDto;
import com.blogging_app.services.CategoryService;
import com.blogging_app.services.PostService;
import com.blogging_app.services.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postService;


	@PostMapping("/add/post/{userId}/{categoryId}")
	ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
		PostDto postDto1 = postService.createPost(postDto, userId, categoryId);
		return ResponseEntity.ok(postDto1);
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
	ResponseEntity<List<PostDto>> getAllPost() {
		List<PostDto> postDtoList = postService.getAllPosts();
		return ResponseEntity.ok(postDtoList);
	}
	@GetMapping("/getallpostbyuser/{userId}")
	ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId) {
		List<PostDto> postDtoList = postService.getAllPostByUser(userId);
		return ResponseEntity.ok(postDtoList);
	}
	@GetMapping("/getallpostbycategory/{categoryId}")
	ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postDtoList = postService.getAllPostByCategory(categoryId);
		return ResponseEntity.ok(postDtoList);
	}
	@DeleteMapping("/delete/{postId}")
	ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse(String.format("Post with id %d deleted successfully", postId), true));
	}
}
