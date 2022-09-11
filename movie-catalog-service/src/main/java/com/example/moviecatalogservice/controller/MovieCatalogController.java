package com.example.moviecatalogservice.controller;


import com.example.moviecatalogservice.model.CatalogItem;
import com.example.moviecatalogservice.model.Movie;
import com.example.moviecatalogservice.model.Rating;
import com.example.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController

@RequestMapping("catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/{userId}")
    public List<CatalogItem> catalogItemsList(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject(
                "http://rating-data-service/ratingsdata/users/ " + userId, UserRating.class);

        return userRating.getUserRating().stream().map(rating -> {

            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());

        })
        .collect(Collectors.toList());
    }
}
