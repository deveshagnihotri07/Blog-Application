package com.blog.application.exceptions;

public class UserNotFoundException extends RuntimeException{

    String resourceName;
    String fieldName;
    String username;
    public UserNotFoundException(String resourceName, String fieldName, String username) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, username));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.username = username;
    }
}
