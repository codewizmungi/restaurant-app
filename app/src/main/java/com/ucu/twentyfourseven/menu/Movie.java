package com.ucu.twentyfourseven.menu;

import android.graphics.drawable.Drawable;

public class Movie {

    public int id;
    public String movieName;
    public String imageLink;
    public String movieGenre;
    public String foodPrice;
    public boolean selected = true;

    public Movie(int id, String movieName, String imageLink, String movieGenre, String foodPrice) {
        this.id = id;
        this.movieName = movieName;
        this.imageLink = imageLink;
        this.movieGenre = movieGenre;
        this.foodPrice = foodPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

}
