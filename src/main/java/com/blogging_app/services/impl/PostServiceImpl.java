package com.blogging_app.services.impl;

import com.blogging_app.entity.Category;
import com.blogging_app.entity.Post;
import com.blogging_app.entity.User;
import com.blogging_app.exceptions.ResourceNotFoundException;
import com.blogging_app.payload.PostDto;
import com.blogging_app.repository.CategoryRepo;
import com.blogging_app.repository.PostRepo;
import com.blogging_app.repository.UserRepo;
import com.blogging_app.services.PostService;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
		Post post = modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setImgName("default.jpg");
		post.setUser(user);
		post.setCategory(category);
		Post newPost = postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
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
	public List<PostDto> getAllPostByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
		List<Post> posts = postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).toList();
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryID) {
		Category category = categoryRepo.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", categoryID));
		List<Post> posts = postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).toList();
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = postRepo.findAll();
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).toList();
		return postDtos;
	}
}
