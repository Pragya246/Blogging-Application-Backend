package com.blogging_app.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	String field;
	int fieldType;

	public ResourceNotFoundException(String field, Integer fieldType) {
		super(String.format("%s not found with id: %d",field, fieldType));
		this.field = field;
		this.fieldType = fieldType;
	}
}
