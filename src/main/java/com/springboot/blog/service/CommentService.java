package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentByPostId(long postId);
	
	CommentDto getCommentById(long postId, long commentId);
	
	CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest);
	
	void deleteCommentById(Long postId, Long commentId);
}
