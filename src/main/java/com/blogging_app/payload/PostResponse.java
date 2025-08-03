package com.blogging_app.payload;

import java.util.List;
import lombok.Data;

@Data
public class PostResponse {

	private List<PostDto> postDtoList;

	private int pageNo;

	private int pageSize;

	private long totalPosts;

	private int totalPages;

	private boolean isLast;
}
