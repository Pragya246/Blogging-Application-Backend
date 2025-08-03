package com.blogging_app.payload;

import java.util.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostDto {

	private int postId;

	private String title;

	private String caption;

	private String imgName;

	private Date addedDate;

	private UserDto user;

	private CategoryDto category;

}
