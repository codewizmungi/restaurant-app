package com.ucu.twentyfourseven.favorites;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.ucu.twentyfourseven.menu.Movie;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartEntry;

public class favoriteHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Movie> cart;

    private static Map<Movie, favoriteEntry> cartMap = new HashMap<Movie, favoriteEntry>();


    public static void setProduct(Movie product) {
        // Get the current cart entry
        favoriteEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products


        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new favoriteEntry(product);
            cartMap.put(product, curEntry);
            return;
        }

    }

    public static List<Movie> getCart() {
        if(cart == null) {
            cart = new Vector<Movie>();
        }

        return cart;
    }

    public static void removeProduct(Movie product) {
        cartMap.remove(product);
    }

    public static List<Movie> getCartList() {
        List<Movie> cartList = new Vector<Movie>(cartMap.keySet().size());
        for(Movie p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }

}