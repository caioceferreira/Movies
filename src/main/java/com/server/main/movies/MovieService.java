package com.server.main.movies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

	@Autowired  
	MovieRepository movieRepository;  

	public List<Movie> getAllMovie(){  
		List<Movie> movies = new ArrayList<>();  
		movieRepository.findAll().forEach(movies::add);
		return movies;  
	}  
	public Movie getMovieById(int id) {
		Optional<Movie> movie = movieRepository.findById(id);
		if(movie.isEmpty()) {
			throw new MovieNotFoundException("id: "+ id);  
		}
		return movie.get();  
	}  
	public Movie saveOrUpdate(Movie movie){  
		return movieRepository.save(movie);  
	}  

	public void delete(int id){  
		movieRepository.deleteById(id);  
	} 
}
