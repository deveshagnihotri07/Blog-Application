package com.blog.application.services.implementation;

import com.blog.application.config.AppConstants;
import com.blog.application.entities.Category;
import com.blog.application.entities.Post;
import com.blog.application.entities.User;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.PostDto;
import com.blog.application.repositories.CategoryRepo;
import com.blog.application.repositories.PostRepo;
import com.blog.application.repositories.UserRepo;
import com.blog.application.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        Post createdPost = this.postRepo.save(post);
        return this.modelMapper.map(createdPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        this.postRepo.save(post);
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String orderBy) {
        Sort sortingOrder = (orderBy.equalsIgnoreCase(AppConstants.DEFAULT_SORT_ORDER) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortingOrder);
        Page<Post> pagePost = this.postRepo.findAll(pageable);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post -> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Post> byCategory = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = byCategory.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        List<Post> byUser = this.postRepo.findByUser(user);
        List<PostDto> postDtos = byUser.stream().map((post -> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchByTitle(String keyword) {
        List<Post> postByTitleContaining = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = postByTitleContaining.stream().map((post -> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchByContent(String keyword) {
        List<Post> postByContentContaining = this.postRepo.searchByContent("%"+keyword+"%");
        List<PostDto> postDtos = postByContentContaining.stream().map((post -> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
        return postDtos;
    }
}
