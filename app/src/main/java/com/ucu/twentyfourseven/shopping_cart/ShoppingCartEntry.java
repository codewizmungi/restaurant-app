package com.ucu.twentyfourseven.shopping_cart;

import com.ucu.twentyfourseven.menu.Movie;

public class ShoppingCartEntry {

    private Movie mProduct;
    private int mQuantity;

    public ShoppingCartEntry(Movie product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
    }

    public Movie getProduct() {
        return mProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}