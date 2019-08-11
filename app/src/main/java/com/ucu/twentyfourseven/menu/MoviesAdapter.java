package com.ucu.twentyfourseven.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ucu.twentyfourseven.R;
import com.ucu.twentyfourseven.favorites.favoriteHelper;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartHelper;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies;
    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";


    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.movieName.setText(movies.get(position).getMovieName());
        holder.movieGenre.setText(movies.get(position).getMovieGenre().substring(0, 20)+"...");
        holder.foodPrice.setText(movies.get(position).getFoodPrice()+" ugx");
        Glide.with(context).load(movies.get(position).getImageLink()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView movieName;
        public TextView movieGenre;
        public TextView foodPrice;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.moviename);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            movieGenre = (TextView) itemView.findViewById(R.id.genre);
            foodPrice = (TextView) itemView.findViewById(R.id.price);

            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            showPopupMenu(v,position);
        }
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_context, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(poaition));
        popup.show();
    }


    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {

        Integer pos;

        public MenuClickListener(int pos) {
            this.pos=pos;
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_favourite:
                    favoriteHelper.setProduct(movies.get(pos));

                    Toast.makeText(context, movies.get(pos).getMovieName() + " is added to favourite", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.action_cart:

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Input a Quantity"); //Set Alert dialog title here
                    alert.setMessage("Enter a Quantity"); //Message here

                    // Set an EditText view to get user input
                    final EditText input = new EditText(context);
                    alert.setView(input);

                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Check to see that a valid quantity was entered
                            int quantity = 0;
                            try {
                                quantity = Integer.parseInt(input.getText()
                                        .toString());

                                if (quantity < 0) {
                                    Toast.makeText(context,
                                            "Please enter a quantity of 0 or higher",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            } catch (Exception e) {
                                Toast.makeText(context,
                                        "Please enter a numeric quantity",
                                        Toast.LENGTH_SHORT).show();

                                return;
                            }

                            // If we make it here, a valid quantity was entered
                            ShoppingCartHelper.setQuantity(movies.get(pos), quantity);
                            Toast.makeText(context, movies.get(pos).getMovieName() + " Has been added to cart", Toast.LENGTH_SHORT).show();

                        } // End of onClick(DialogInterface dialog, int whichButton)
                    }); //End of alert.setPositiveButton
                    alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                            dialog.cancel();
                        }
                    }); //End of alert.setNegativeButton
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                    return true;

                case R.id.action_info:
                    Intent intent = new Intent(context, MenuInfo.class);
                    intent.putExtra("name", movies.get(pos).getMovieName());
                    intent.putExtra("image", movies.get(pos).getImageLink());
                    intent.putExtra("description", movies.get(pos).getMovieGenre());
                    context.startActivity(intent);

                    return true;
                default:
            }
            return false;
        }
    }



}
