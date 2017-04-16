package com.ucu.twentyfourseven.menu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ucu.twentyfourseven.AlertDialogManager;
import com.ucu.twentyfourseven.ConnectionDetector;
import com.ucu.twentyfourseven.R;
import com.ucu.twentyfourseven.favorites.FavoriteActivity;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private MoviesAdapter adapter;

    String category;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    // Alert Dialog Manager
    AlertDialogManager alert =new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActivity.this, "Internet Connection Error",
                    "Please check your connection", false);
            // stop executing code by return
            return;
        }

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            category = extras.getString("category");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        movies = new ArrayList<>();
        getMoviesFromDB(0);

        gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);

        adapter = new MoviesAdapter(this, movies);
        recyclerView.setAdapter(adapter);

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayout.findLastCompletelyVisibleItemPosition() == movies.size() - 1) {
                    getMoviesFromDB(movies.get(movies.size() - 1).getId());
                }

            }
        });*/

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
            Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.favorites) {

            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getMoviesFromDB(int id) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... movieIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://chakusoza.com/devs/ucu/getMenu.php?category=" + category)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Movie movie = new Movie(object.getInt("food_id"), object.getString("food_title"),
                                object.getString("food_image"), object.getString("food_description"), object.getString("food_price"));

                        MainActivity.this.movies.add(movie);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        asyncTask.execute(id);
    }


}
