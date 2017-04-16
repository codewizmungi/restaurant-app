package com.ucu.twentyfourseven.favorites;

import com.ucu.twentyfourseven.menu.Movie;

public class favoriteEntry {

    private Movie mProduct;

    public favoriteEntry(Movie product) {
        mProduct = product;
    }

    public Movie getProduct() {
        return mProduct;
    }

}
