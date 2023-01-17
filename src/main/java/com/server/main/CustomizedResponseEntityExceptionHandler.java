package com.server.main;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.server.main.exception.ExceptionResponse;
import com.server.main.movies.MovieNotFoundException;

@ControllerAdvice
@RestController 
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(Exception.class)  
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request)  {  
		ExceptionResponse exceptionResponse= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));  
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); 
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(MovieNotFoundException.class)
	public final ResponseEntity<Object> handleMovieNotFoundExceptions(MovieNotFoundException ex, WebRequest request)  {    
		ExceptionResponse exceptionResponse= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));  
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);  
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)   {  
		ExceptionResponse exceptionResponse= new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString());    
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);   
	}  
}
