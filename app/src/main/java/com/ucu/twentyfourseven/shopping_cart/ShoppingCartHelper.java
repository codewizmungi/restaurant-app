package com.ucu.twentyfourseven.shopping_cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.ucu.twentyfourseven.menu.Movie;

public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static Map<Movie, ShoppingCartEntry> cartMap = new HashMap<Movie, ShoppingCartEntry>();

    public static void setQuantity(Movie product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if(quantity <= 0) {
            if(curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Movie product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        if(curEntry != null)
            return curEntry.getQuantity();

        return 0;
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