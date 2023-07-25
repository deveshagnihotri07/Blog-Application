package com.blog.application.controllers;

import com.blog.application.config.AppConstants;
import com.blog.application.payloads.ApiResponse;
import com.blog.application.payloads.PostDto;
import com.blog.application.services.FileService;
import com.blog.application.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Value("${project.image}")
    private String path;

    @Autowired
    PostService postService;
    @Autowired
    FileService fileService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){

        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    //Get By User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getByUserId(@PathVariable Integer userId){
        List<PostDto> allPostByUser = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(allPostByUser, HttpStatus.OK);
    }

    //Get By Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getByCategoryId(@PathVariable Integer categoryId){
        List<PostDto> allPostByCategory = this.postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(allPostByCategory, HttpStatus.OK);
    }

    //Get All Posts
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT__POST_SORT_BY, required = false) String sortBy,
                                                     @RequestParam(value = "orderBy", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String orderBy){
        List<PostDto> allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, orderBy);
        return new ResponseEntity<>(allPost, HttpStatus.OK);
    }

    //Get Post By ID
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getAllPosts(@PathVariable Integer postId){
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //Delete Post
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Deleted Successfully", true);
    }

    //Update Post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable Integer postId){
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    //Search By Title
    @GetMapping("/posts/search/title/{keywords}")
    public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String keywords){
        List<PostDto> postDtos = this.postService.searchByTitle(keywords);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    //Search By Content
    @GetMapping("/posts/search/content/{keywords}")
    public ResponseEntity<List<PostDto>> searchByContent(@PathVariable String keywords){
        List<PostDto> postDtos = this.postService.searchByContent(keywords);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable("postId") Integer postId) throws Exception {
        PostDto postById = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postById.setImageName(fileName);
        PostDto updatedPost = postService.updatePost(postById, postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);

    }
}
