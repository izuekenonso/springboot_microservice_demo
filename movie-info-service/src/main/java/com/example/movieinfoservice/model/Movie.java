package com.example.movieinfoservice.model;

public class Movie {

    private String movieId;
    private String desc;
    private String name;

    public Movie(String movieId, String desc, String name) {
        this.movieId = movieId;
        this.desc = desc;
        this.name = name;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
