package com.server.main.movies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.bind.annotation.DeleteMapping;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import com.server.main.award.Award;
import com.server.main.award.AwardInterval;  


@RestController  
public class MovieJPAResource{  

	@Autowired  
	MovieService movieService;
	public static final String YES = "yes";
	public static final String AND = " and";
	public static final String COMMA = ",";
	private Movie previousMovie = null;
	private Movie followingMovie = null;

	@GetMapping("movie")  
	public List<Movie> getAllMovie(){  
		return movieService.getAllMovie();  
	}  
	@GetMapping("movie/{id}")  
	public Movie getMovie(@PathVariable("id") int id) {  
		return movieService.getMovieById(id);  
	}  

	@DeleteMapping("movie/{id}")  
	public void deleteMovie(@PathVariable("id") int id) {  
		movieService.delete(id);  
	}  

	@PostMapping("movie")  
	public Integer saveMovie(@RequestBody Movie movie) {  
		movieService.saveOrUpdate(movie);  
		return movie.getId();  
	} 


	@GetMapping("movie/awards") 
	public AwardInterval getApiMovie(){  
		List<Movie> allMovie = movieService.getAllMovie(); 
		Set<Award> allAwards = new HashSet<>();

		if(allMovie.isEmpty()) {
			return new AwardInterval();
		}
		List<Movie> winnerMovies = new ArrayList<>();		
		Set<Movie> filteredMovies = findWinners(allMovie);

		for(Movie movie : filteredMovies) {
			winnerMovies.add(movie);
		}

		List<String> producers = findWhoWonMoreThanOnce(winnerMovies);

		for(String producer : producers) {
			findPreviousAndFollowingWins(producer, winnerMovies, allAwards);
		}

		Award min = Collections.min(allAwards);
		Award max = Collections.max(allAwards);

		return new AwardInterval (min, max);

	} 

	private Set<Movie> findWinners(List<Movie> movie) { 
		final Set<Movie> setToReturn = new HashSet<>(); 

		for (Movie item : movie) {
			if (Boolean.TRUE.equals(item.getWinner()) && item.getWinner() != null) {
				setToReturn.add(item);
			}
		}    	    
		return setToReturn;
	}

	private List<String> findWhoWonMoreThanOnce(List<Movie> winnerMovies){		
		Map<String, Integer> nameAndCount = new HashMap<>();
		List<String> producers  = new ArrayList<>();
		List<String> producersFiltered = new ArrayList<>();

		addProducersName(winnerMovies, producers);
		countTimesTheSameNameAppears(producers, nameAndCount );
		countTimesProducerWon(producersFiltered, nameAndCount);

		return producersFiltered;
	}

	private void addProducersName(List<Movie> movies, List<String> producers) {
		for(Movie movie : movies) {
			splitIfMoreThanOneProducer(movie.getProducers(), producers);
		}
	}

	private void splitIfMoreThanOneProducer(String producersToSplit, List<String> producers) {
		String[] producerCommaSplit = null;

		if(producersToSplit.contains(AND)){
			producerCommaSplit = splitAndFromProducers(producersToSplit, producers);

			if(producerCommaSplit != null) {
				splitCommaFromProducers(producerCommaSplit, producers);
			}

		}
		else {
			producers.add(producersToSplit.trim());
		}
	}

	private String[] splitAndFromProducers(String producersToSplit, List<String> producers) {
		String[] producerCommaSplit = null;

		String[] producersSplit = producersToSplit.split(AND);
		for(String addProducerSplit : producersSplit) {
			if(addProducerSplit.contains(",")) {
				producerCommaSplit = addProducerSplit.split(COMMA);
			}
			if(!addProducerSplit.contains(COMMA) && !addProducerSplit.equalsIgnoreCase(AND)) {
				producers.add(addProducerSplit.trim());
			}
		}

		return producerCommaSplit;
	}

	private void splitCommaFromProducers(String[] producerCommaSplit, List<String> producers) {
		for(String addProducerSplit : producerCommaSplit ) {
			if(!addProducerSplit.equalsIgnoreCase(COMMA)) {
				producers.add(addProducerSplit.trim());
			}
		}
	}

	private void countTimesTheSameNameAppears(List<String> producers, Map<String, Integer>  nameAndCount) {
		for (String producer : producers) {
			Integer count = nameAndCount.get(producer);
			if (count == null) {
				nameAndCount.put(producer, 1);
			} else {
				nameAndCount.put(producer, ++count);
			}
		}
	}

	private void countTimesProducerWon(List<String> producersFiltered, Map<String, Integer>  nameAndCount) {
		Set<Entry<String, Integer>> entrySet = nameAndCount.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			if (entry.getValue() > 1) {
				producersFiltered.add(entry.getKey());
			}
		}
	}

	private void findPreviousAndFollowingWins(String producer, List<Movie> winnerMovies, Set<Award>allAwards) {
		resetPreviousAndFollowingMovies();
		findPreviousMovie(producer, winnerMovies);
		findFollowingOrUpdatePreviousMovie(producer, winnerMovies);
		createIntervalAndSetAward(producer, allAwards);
	}

	private void resetPreviousAndFollowingMovies() {
		previousMovie= null;
		followingMovie = null;
	}

	private void findPreviousMovie(String producer, List<Movie> winnerMovies) {
		for(Movie movie : winnerMovies) {
			if(movie.getProducers().contains(producer) && getPreviousMovie() == null ) {
				setPreviousMovie(movie);
			}
			if(getPreviousMovie() != null && movie.getProducers().contains(producer) && movie.getReleaseyear() < getPreviousMovie().getReleaseyear()  ) {
				setPreviousMovie(movie);
			}
		}
	}

	private void findFollowingOrUpdatePreviousMovie(String producer, List<Movie> winnerMovies) {
		for(Movie movie : winnerMovies) {
			if(movie.getProducers().contains(producer) && getPreviousMovie() != null && !getPreviousMovie().equals(movie) && movie.getReleaseyear() < getPreviousMovie().getReleaseyear() ) {
				setFollowingMovie(getPreviousMovie());
				setPreviousMovie(movie);
			}
			if(movie.getProducers().contains(producer) && getPreviousMovie() != null && !getPreviousMovie().equals(movie) && movie.getReleaseyear() > getPreviousMovie().getReleaseyear()) {
				setFollowingMovie(movie);
			}
		}
	}

	private void createIntervalAndSetAward(String producer, Set<Award>allAwards) {
		Integer interval;
		if(getFollowingMovie() != null) {
			interval  = followingMovie.getReleaseyear() - previousMovie.getReleaseyear();
			Award award = new Award(producer, interval, previousMovie.getReleaseyear(), followingMovie.getReleaseyear());	
			allAwards.add(award);
		}
	}

	public Movie getPreviousMovie() {
		return previousMovie;
	}
	public void setPreviousMovie(Movie previousMovie) {
		this.previousMovie = previousMovie;
	}
	public Movie getFollowingMovie() {
		return followingMovie;
	}
	public void setFollowingMovie(Movie followingMovie) {
		this.followingMovie = followingMovie;
	}
}  