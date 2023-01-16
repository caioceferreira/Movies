package com.server.main;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.server.main.award.AwardInterval;
import com.server.main.movies.Movie;
import com.server.main.movies.MovieJPAResource;
import com.server.main.movies.MovieService;

@SpringBootTest
class TexoApplicationTests {

	@Autowired
	private  MovieService movieService;

	@Autowired
	private MovieJPAResource movieJPAResource;

	@Test
	@BeforeTestClass
	void contextLoads() {
		assertThat(movieService).isNotNull();
		assertThat(movieJPAResource).isNotNull();
	}

	@Test
	void getAllMovie(){  
		assertThat(movieService.getAllMovie()).isNotEmpty();  
		assertThat(movieService.getAllMovie()).isNotNull();
	}  

	@Test
	void getMovieById() {
		assertThat(movieService.getMovieById(generateRandomIntegerOneToFifty())).isNotNull();
		assertThat(movieJPAResource.getMovie(generateRandomIntegerOneToFifty())).isNotNull();
	}

	@Test
	void saveOrUpdate() {
		assertThat(movieService.saveOrUpdate(movieService.getMovieById(generateRandomIntegerOneToFifty()))).isInstanceOf(Movie.class);
		assertThat(movieJPAResource.saveMovie(movieService.getMovieById(generateRandomIntegerOneToFifty()))).isInstanceOf(Integer.class);
	}

	@Test
	void getApiMovie() {
		assertThat(movieJPAResource.getApiMovie()).isInstanceOf(AwardInterval.class);
	}

	private Integer generateRandomIntegerOneToFifty() {
		Integer min = 1;
		Integer max = 50;
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}
