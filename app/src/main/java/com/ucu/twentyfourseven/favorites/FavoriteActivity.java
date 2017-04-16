package com.ucu.twentyfourseven.favorites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ucu.twentyfourseven.AlertDialogManager;
import com.ucu.twentyfourseven.ConnectionDetector;
import com.ucu.twentyfourseven.menu.MenuActivity;
import com.ucu.twentyfourseven.R;
import com.ucu.twentyfourseven.menu.Movie;
import com.ucu.twentyfourseven.shopping_cart.MenuAdapter;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartActivity;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartHelper;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    private List<Movie> mCartList;
    private favoriteAdapter mProductAdapter;

    // Alert Dialog Manager
    AlertDialogManager alert =new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet Connection is not present
            alert.showAlertDialog(FavoriteActivity.this, "Internet Connection Error",
                    "Please check your connection", false);
            // stop executing code by return
            return;
        }

        mCartList = favoriteHelper.getCartList();

        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewFavorites);


        mProductAdapter = new favoriteAdapter(mCartList, getLayoutInflater(), true);
        listViewCatalog.setAdapter(mProductAdapter);

        Button removeButton = (Button) findViewById(R.id.ButtonRemoveFromCart);

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Total No. of Favorites: " + mCartList.size());

        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Movie selectedProduct = mCartList.get(position);
                if(selectedProduct.selected == true)
                    selectedProduct.selected = false;
                else
                    selectedProduct.selected = true;

                mProductAdapter.notifyDataSetInvalidated();

            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                for(int i=mCartList.size()-1; i>=0; i--) {

                    if(mCartList.get(i).selected) {
                        Movie m = mCartList.get(i);
                        favoriteHelper.removeProduct(m);

                        mCartList.remove(i);
                    }
                }
                mProductAdapter.notifyDataSetChanged();
            }
        });

    }

}
