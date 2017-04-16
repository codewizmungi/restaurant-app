package com.ucu.twentyfourseven.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.Glide;
import com.ucu.twentyfourseven.R;
import com.ucu.twentyfourseven.favorites.FavoriteActivity;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartActivity;

public class MenuInfo extends AppCompatActivity {

    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_info);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("name");
        String description = extras.getString("description");
        String imagetext = extras.getString("image");



        ImageView image = (ImageView) findViewById(R.id.imageViewFood);
        TextView tit = (TextView) findViewById(R.id.title);
        TextView des = (TextView) findViewById(R.id.description);

        Glide.with(MenuInfo.this).load(imagetext).into(image);


        tit.setText(title);
        des.setText(description);

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
            Intent intent = new Intent(MenuInfo.this, ShoppingCartActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.favorites) {

            Intent intent = new Intent(MenuInfo.this, FavoriteActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
