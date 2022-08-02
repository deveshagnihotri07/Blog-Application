package com.blog.application.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	String resourceName;
	String fieldName;
	Integer fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldvalue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldvalue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldvalue;
	}

}
