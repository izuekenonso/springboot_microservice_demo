package com.example.ratingdataservice.controller;

import model.Rating;
import model.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("ratingsdata")
public class RatingController {

    @GetMapping(value="/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 2);
    }

    @GetMapping(value="users/{userId}")
    public UserRating getRatingByUserId(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("001", 4),
                new Rating("002", 5)
        );

        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);

        return userRating;
    }
}
