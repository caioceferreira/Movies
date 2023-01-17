package com.server.main.movies;
import org.springframework.http.HttpStatus;  
import org.springframework.web.bind.annotation.ResponseStatus;  

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3934113831567693147L;

	public MovieNotFoundException(String message) {
		super(message);
	}
}
