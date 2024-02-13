package com.my.controller;

import com.my.payload.CommentDto;
import com.my.service.CommentService;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comments?postId=1
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam("postId") long postId, @RequestBody CommentDto commentDto){

         CommentDto dto=commentService.createComment(postId, commentDto);
         return new ResponseEntity<>(dto, HttpStatus.CREATED);//201
    }
    //http://localhost:8080/api/comments?postId=1
    @GetMapping
    public List<CommentDto> getCommentsByPostId(@RequestParam(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    //http://localhost:8080/api/comments/1
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId){
        List<CommentDto> commentDto = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    //http://localhost:8080/api/comments?postId=1
    @GetMapping("comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@RequestParam Long postId){
        List<CommentDto> commentDto = commentService.getAllComments();
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

//    //http://localhost:8080/api/posts/1/comments/2
//    @DeleteMapping("/comments/{commentId}")
//    public ResponseEntity<String> deleteCommentById(
//            @PathVariable(value = "commentId") Long id){
//        commentService.deleteCommentById(id);
//        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
//    }

    //http://localhost:8080/api/comments/1
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity("Comment deleted successfully", HttpStatus.OK);
    }

}


