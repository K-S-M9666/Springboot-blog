package com.springboot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostDtov2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController

@Tag(
		
		name="Crud Rest APIs for Post Resources"
		)
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@Operation(summary="Create post REST API",
			    description="Create post REST API is used to save post into database ")
	@ApiResponse(
			  responseCode ="201",
			  description = "Http status 201 created"
			)
	
	@SecurityRequirement(
			name="Bear Authentication"
			)
	//create blog post
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/v1/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto)
	{
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	
	@Operation(summary="Get All posts REST API",
		    description="Get All post by Id REST API is used to fetch All post from the database ")
    @ApiResponse(
		  responseCode ="200",
		  description = "Http status 200 success"
		)
	//get All post rest API
	@GetMapping("/api/v1/posts")
	public PostResponse getAllPosts(@RequestParam(value="pageNo" ,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required=false) int pageNo,
			                        @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
			                        @RequestParam(value="sortBy" , defaultValue = AppConstants.DEFAULT_SORT_BY , required=false) String sortBy,
			                        @RequestParam(value="sortDir" , defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			)
	                                
	{
		return postService.getAllPosts(pageNo,pageSize, sortBy, sortDir);
	}
	
	
	@Operation(summary="Get post by Id REST API",
		    description="Get post by Id REST API is used to get single post from the database ")
    @ApiResponse(
		  responseCode ="200",
		  description = "Http status 200 success"
		)
	//get Post by id
	@GetMapping("/api/v1/posts/{id}")
	//@GetMapping(value ="/api/posts/{id}", params = "version=1")
	//@GetMapping(value ="/api/posts/{id}", headers = "X-API-VERSION=1")
	//@GetMapping(value = "/api/posts/{id}", produces = "application/vnd.javaguides.v1+json")
	public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name="id") long id)
	{
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	//version 2
	//@GetMapping("/api/v2/posts/{id}")
	//@GetMapping(value ="/api/posts/{id}", params = "version=2")
	//@GetMapping(value ="/api/posts/{id}", headers = "X-API-VERSION=2")
	@GetMapping(value = "/api/posts/{id}", produces = "application/vnd.javaguides.v2+json")
	public ResponseEntity<PostDtov2> getPostByIdV2(@PathVariable(name="id") long id)
	{
		PostDto postDto= postService.getPostById(id);
		PostDtov2 postDtov2 = new PostDtov2();
		postDtov2.setId(postDto.getId());
		postDtov2.setTitle(postDto.getTitle());
		postDtov2.setDescription(postDto.getDescription());
		postDtov2.setContent(postDto.getContent());
		List<String> tags = new ArrayList<>();
		tags.add("Java");
		tags.add("Springboot");
		tags.add("Aws");
		postDtov2.setTags(tags);
		return ResponseEntity.ok(postDtov2);
	}
	
	@Operation(summary="Update post by Id REST API",
		    description="Update post by Id REST API is used to update particular post from the database ")
    @ApiResponse(
		  responseCode ="200",
		  description = "Http status 200 success"
		)
	
	@SecurityRequirement(
			name="Bear Authentication"
			)
	//update post by id
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id)
	{
		PostDto postResponse = postService.updatePost(postDto, id);
		
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
	}
	
	@Operation(summary="Delete post by Id REST API",
		    description="Delete post by Id REST API is used to Delete particular post from the database ")
    @ApiResponse(
		  responseCode ="200",
		  description = "Http status 200 success"
		)
	@SecurityRequirement(
			name="Bear Authentication"
			)
	//delete post rest api by id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/v1/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name="id") long id)
	{
		postService.deletePostById(id);
		return new ResponseEntity<>("Post Entity Deleted Sucessfully", HttpStatus.OK);
	}
	
	//build Get Post By category REst api
	@GetMapping("/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId)
	{
		List<PostDto> postDto = postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(postDto);
	}
}
