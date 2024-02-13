package com.my.service;

import com.my.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

   List<CommentDto> getCommentsByPostId(long postId);


    List<CommentDto> getCommentByPostId(long postId);

    List<CommentDto> getAllComments();

    void deleteCommentById(Long commentId);

    // void deleteCommentById( Long commentId);

}
