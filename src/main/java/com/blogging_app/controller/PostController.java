package com.blogging_app.controller;

import com.blogging_app.config.AppConstants;
import com.blogging_app.payload.ApiResponse;
import com.blogging_app.payload.PostDto;
import com.blogging_app.payload.PostResponse;
import com.blogging_app.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/addpost/{userId}/{categoryId}")
	ResponseEntity<PostDto> createPost(@Valid @ModelAttribute PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId, @RequestParam("file") MultipartFile file) throws IOException {
		PostDto postDto1 = postService.createPost(postDto, userId, categoryId, path, file);
		return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
	}

	@GetMapping(value = "/getimage/{filename}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public void getImage(@PathVariable String filename, HttpServletResponse response) throws IOException {
		InputStream inputStream = postService.downloadImage(path, filename);
		String fileExtension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
		if ("png".equals(fileExtension)) {
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
		} else if ("jpeg".equals(fileExtension) || "jpg".equals(fileExtension)) {
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		} else {
			response.setContentType("application/octet-stream");
		}
		StreamUtils.copy(inputStream, response.getOutputStream());
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
