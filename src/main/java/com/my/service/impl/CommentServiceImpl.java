package com.my.service.impl;

import com.my.entity.Comment;
import com.my.entity.Post;
import com.my.exception.ResourceNotFound;
import com.my.payload.CommentDto;
import com.my.repository.CommentRepository;
import com.my.repository.PostRepository;
import com.my.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );
        comment.setPost(post);

        Comment SavedComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(SavedComment);
        
        return null;
        
    }

    private CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = mapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);

        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return null;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

//    @Override
//    public void deleteCommentById(Long commentId) {
//            Optional<Comment> commentOptional = commentRepository.findById(commentId);
//
//            if (commentOptional.isPresent()) {
//                Comment comment = commentOptional.get();
//                commentRepository.delete(comment);
//            } else {
//                throw new ResourceNotFound("Comment not found with id: " + commentId);
//            }
//        }
//    }

    @Override
    public void deleteCommentById(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            // If the comment exists, delete it
            commentRepository.deleteById(commentId);
        } else {
            throw new ResourceNotFound("Comment not found with id: " + commentId);
        }

    }

}





