package com.springboot.blog.payload;

import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDtov2 {

	private long id;
	
	//title should not be empty
	//title should have atleast 2 characters
	@Schema(
			description = "Blog post title"
			)
	@NotEmpty
	@Size(min=2, message="Post title should have at least 2 characters")
	private String title;
	
	@Schema(
			description="Blog post Description"
			)
	@NotEmpty
	@Size(min=10, message="Post Description should have atleast 10 characters")
	private String description;
	
	@Schema(
			description="Blog post content"
			)
	@NotEmpty
	private String content;
	
	private Set<CommentDto> comments;
	
	@Schema(
			description ="Blog post Category"
			)
	private Long categoryId;
	private List<String> tags;
	
	
	
	
	
}
