package com.blog.application.services;

import com.blog.application.payloads.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto post, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto post, Integer postId);

    void deletePost(Integer postId);

    List<PostDto> getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String orderBy);

    PostDto getPostById(Integer postId);

    List<PostDto> getAllPostByCategory(Integer categoryId);

    List<PostDto> getAllPostByUser(Integer userId);

    List<PostDto> searchByTitle(String keyword);

    List<PostDto> searchByContent(String keyword);
}
