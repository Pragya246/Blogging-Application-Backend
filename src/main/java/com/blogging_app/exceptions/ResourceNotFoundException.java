package com.blogging_app.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	String field;
	String fieldType;

	public ResourceNotFoundException(String field, String  fieldType) {
		super(String.format("%s not found with id: %s",field, fieldType));
		this.field = field;
		this.fieldType = fieldType;
	}
}
