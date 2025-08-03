package com.blogging_app.exceptions;

import com.blogging_app.payload.ApiResponse;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		String error = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(error, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(objectError -> {
			String fieldName = ((FieldError) objectError).getField();
			String message = objectError.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		String error = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(error, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ApiResponse> handleNullPointerException(NullPointerException ex) {
//		String error = ex.getMessage();
		String error = "Service: [ServiceName] - " + ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(error, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ApiResponse> handleFileNotFoundException(FileNotFoundException error) {
		ApiResponse apiResponse = new ApiResponse(error.getMessage(), false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

}
