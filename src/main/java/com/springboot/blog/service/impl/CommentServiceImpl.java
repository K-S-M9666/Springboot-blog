package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRespository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRespository, ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRespository = postRespository;
		this.mapper= mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);
		// retrieve post entity by id
		Post post = postRespository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		// set post to comment entity
		comment.setPost(post);

		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);
	}

	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		// retrieve comment by postId
		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		// retrieve post entity by id
		Post post = postRespository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comment by id

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {

		Post post = postRespository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());

		Comment updatedComment = commentRepository.save(comment);

		return mapToDto(updatedComment);
	}

	@Override
	public void deleteCommentById(Long postId, Long commentId) {

		Post post = postRespository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}

		commentRepository.delete(comment);
	}

	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
		/*
		 * CommentDto commentDto = new CommentDto();
		 * commentDto.setId(comment.getId()); commentDto.setName(comment.getName());
		 * commentDto.setBody(comment.getBody());
		 * commentDto.setEmail(comment.getEmail());
		 */

		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		
		Comment comment = mapper.map(commentDto, Comment.class);
		
		/*
		 * Comment comment = new Comment(); comment.setId(commentDto.getId());
		 * comment.setName(commentDto.getName());
		 * comment.setEmail(commentDto.getEmail());
		 * comment.setBody(commentDto.getBody());
		 */

		return comment;
	}
}
