package com.ucu.twentyfourseven.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.ucu.twentyfourseven.R;
import com.ucu.twentyfourseven.favorites.FavoriteActivity;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton drinks = (ImageButton) findViewById(R.id.btn_drinks);
        ImageButton snacks = (ImageButton) findViewById(R.id.btn_snack);
        ImageButton local_food = (ImageButton) findViewById(R.id.btn_local_food);

        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("category", "drinks");
                startActivity(intent);

            }
        });

        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("category", "snacks");
                startActivity(intent);
            }
        });

        local_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("category", "local_food");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar itemcart clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.view_cart) {
            Intent intent = new Intent(MenuActivity.this, ShoppingCartActivity.class);
            startActivity(intent);

            return true;
        }

        if (id == R.id.favorites) {
            Intent intent = new Intent(MenuActivity.this, FavoriteActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
