package com.blog.application.exceptions;

public class BadCredsException extends RuntimeException{

    public BadCredsException(String message) {
        super(message);
    }
}
