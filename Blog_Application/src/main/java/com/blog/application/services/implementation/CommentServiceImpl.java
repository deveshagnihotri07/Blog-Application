package com.blog.application.services.implementation;

import com.blog.application.entities.Comment;
import com.blog.application.entities.Post;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.CommentDto;
import com.blog.application.repositories.CommentRepo;
import com.blog.application.repositories.PostRepo;
import com.blog.application.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
//        System.out.println("Inside Comment service : "+commentDto.toString());
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));
        commentRepo.delete(comment);
    }
}
