package com.blogging_app.services.impl;

import com.blogging_app.entity.Category;
import com.blogging_app.entity.Post;
import com.blogging_app.entity.User;
import com.blogging_app.exceptions.ResourceNotFoundException;
import com.blogging_app.payload.PostDto;
import com.blogging_app.payload.PostResponse;
import com.blogging_app.repository.CategoryRepo;
import com.blogging_app.repository.PostRepo;
import com.blogging_app.repository.UserRepo;
import com.blogging_app.services.PostService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepo postRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId, String path, MultipartFile file) throws IOException {
		Post post = modelMapper.map(postDto, Post.class);
		String filePath=path+file.getOriginalFilename();
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		Post savedPost = postRepo.save(post.builder()
				.title(postDto.getTitle())
				.caption(postDto.getCaption())
				.addedDate(new Date())
				.imgName(file.getOriginalFilename())
				.user(userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId)))
				.category(categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", categoryId)))
				.build());
		System.out.println(path);
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
		post.setTitle(postDto.getTitle());
		post.setCaption(postDto.getCaption());
		Post savedPost = postRepo.save(post);
		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
		postRepo.delete(post);
	}

	@Override
	public PostDto getpostById(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPostByUser(Integer userId, Integer pageNo, Integer pageSize, String sortBy) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return getPostResponse(postRepo.findByUser(user, paging));
	}

	@Override
	public PostResponse getAllPostByCategory(Integer categoryID, Integer pageNo, Integer pageSize, String sortBy) {
		Category category = categoryRepo.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", categoryID));
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return getPostResponse(postRepo.findByCategory(category, paging));
	}

	@Override
	public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return getPostResponse(postRepo.findAll(paging));
	}

	@Override
	public PostResponse searchPost(String keyword, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
		Sort.Direction direction = Sort.Direction.fromString(sortDir);
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		return getPostResponse(postRepo.findByTitleContaining(keyword, paging));
	}

	@Override
	public InputStream downloadImage(String path, String fileName) throws IOException {
		String filePath=path+fileName;
		return Files.newInputStream(new File(filePath).toPath());
	}

	private String uploadImage(MultipartFile file, String path) throws IOException {
		// upload image logic
		String name = file.getOriginalFilename();
		String filename = UUID.randomUUID().toString().concat(String.valueOf(name.lastIndexOf(".")));
		File file1 = new File(path);
		if(!file1.exists())
			file1.mkdirs();
		String filePath = path +File.separator+ filename;
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return name;
	}

	private PostResponse getPostResponse(Page<Post> pagedResult) {
		List<Post> posts = pagedResult.hasContent() ? pagedResult.getContent() : List.of();
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).toList();
		PostResponse postResponse = new PostResponse();
		postResponse.setPostDtoList(postDtos);
		postResponse.setTotalPosts(pagedResult.getTotalElements());
		postResponse.setTotalPages(pagedResult.getTotalPages());
		postResponse.setPageNo(pagedResult.getNumber());
		postResponse.setPageSize(pagedResult.getSize());
		postResponse.setLast(pagedResult.isLast());
		return postResponse;
	}

}
