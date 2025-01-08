package com.blogging_app.services;

import com.blogging_app.entity.Category;
import com.blogging_app.entity.User;
import com.blogging_app.payload.PostDto;
import java.util.List;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	void deletePost(Integer postId);
	PostDto getpostById(Integer postId);
	List<PostDto> getAllPostByUser(Integer userId);
	List<PostDto> getAllPostByCategory(Integer categoryID);
	List<PostDto> getAllPosts();
}
