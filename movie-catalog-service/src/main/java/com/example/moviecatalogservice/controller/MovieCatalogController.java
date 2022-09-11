package com.example.moviecatalogservice.controller;


import com.example.moviecatalogservice.model.CatalogItem;
import com.example.moviecatalogservice.model.Movie;
import com.example.moviecatalogservice.model.UserRating;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController

@RequestMapping("catalog")
public class MovieCatalogController {
	
	private static final String MOVIE_CATALOG_SERVICE = "movieCatalogService";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/{userId}")
    @CircuitBreaker(name=MOVIE_CATALOG_SERVICE, fallbackMethod= "catalogItemListFallback")
    public List<CatalogItem> catalogItemsList(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject(
                "http://rating-data-service/ratingsdata/users/ " + userId, UserRating.class);

        return userRating.getUserRating().stream().map(rating -> {

            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());

        })
        .collect(Collectors.toList());
    }
    
    
    public List<CatalogItem> catalogItemListFallback(Exception e) {
    	
    	CatalogItem catalogItem = new CatalogItem("Movie Catalog Service is down", "no value", 0);
    	
    	List<CatalogItem> dummyCatalogItem = Arrays.asList(catalogItem);
    			
    	return dummyCatalogItem;
    }
}
