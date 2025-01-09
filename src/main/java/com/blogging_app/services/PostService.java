package com.blogging_app.services;

import com.blogging_app.entity.Category;
import com.blogging_app.entity.User;
import com.blogging_app.payload.PostDto;
import com.blogging_app.payload.PostResponse;
import java.util.List;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	void deletePost(Integer postId);
	PostDto getpostById(Integer postId);
	PostResponse getAllPostByUser(Integer userId, Integer pageNo, Integer pageSize, String sortBy);
	PostResponse getAllPostByCategory(Integer categoryID, Integer pageNo, Integer pageSize, String sortBy);
	PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy);
	PostResponse searchPost(String keyword, Integer pageNo, Integer pageSize, String sortBy, String sortDir);
}
