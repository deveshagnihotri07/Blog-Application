package com.blog.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.application.payloads.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionhandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourseNotFoundExceptionHandler(ResourceNotFoundException ex){
		String messageString = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(messageString, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgNotValidExceptionHandler(MethodArgumentNotValidException ex){
		Map<String, String> response = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			response.put(fieldName, errorMessage);
		});

		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredsException.class)
	public ResponseEntity<ApiResponse> badCredsExceptionHandler(BadCredsException ex){
		String messageString = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(messageString, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}
