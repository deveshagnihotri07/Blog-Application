package com.blog.application.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	String resourceName;
	String fieldName;
	Integer fieldvalue;
	public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldvalue) {
		super(String.format("%s not found with %s : %l", resourceName, fieldName, fieldvalue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldvalue = fieldvalue;
	}

}
