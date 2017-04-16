package com.ucu.twentyfourseven.shopping_cart;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.ucu.twentyfourseven.AlertDialogManager;
import com.ucu.twentyfourseven.ConnectionDetector;
import com.ucu.twentyfourseven.Payment;
import com.ucu.twentyfourseven.favorites.favoriteHelper;
import com.ucu.twentyfourseven.menu.MainActivity;
import com.ucu.twentyfourseven.menu.MenuActivity;
import com.ucu.twentyfourseven.menu.Movie;
import com.ucu.twentyfourseven.R;
import com.google.api.client.util.StringUtils;

public class ShoppingCartActivity extends ActionBarActivity {

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    private List<Movie> mCartList;
    private MenuAdapter mProductAdapter;

    // Alert Dialog Manager
    AlertDialogManager alert =new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet Connection is not present
            alert.showAlertDialog(ShoppingCartActivity.this, "Internet Connection Error",
                    "Please check your connection", false);
            // stop executing code by return
            return;
        }

        mCartList = ShoppingCartHelper.getCartList();

        // Make sure to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }


        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);


        mProductAdapter = new MenuAdapter(mCartList, getLayoutInflater(), true, true);
        listViewCatalog.setAdapter(mProductAdapter);

        Button removeButton = (Button) findViewById(R.id.ButtonRemoveFromCart);
        Button proceedButton = (Button) findViewById(R.id.Button02);

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Movie selectedProduct = mCartList.get(position);
                if(selectedProduct.selected == true) {
                    selectedProduct.selected = false;
                }
                else {
                    selectedProduct.selected = true;
                }

                mProductAdapter.notifyDataSetInvalidated();

            }
        });


        proceedButton.setOnClickListener(new View.OnClickListener() {

            String name = " ";
            int subTotal = 0;

            @Override
            public void onClick(View v) {

                if (mCartList.size() > 0){
                    AlertDialog.Builder alert = new AlertDialog.Builder(ShoppingCartActivity.this);
                    alert.setTitle(Html.fromHtml("<b>Order Confirmation</b>")); //Set Alert dialog title here
                    alert.setMessage(Html.fromHtml("Finished Selection!<br/> Do you wish to Proceed to Payment?"));

                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            for(int i=0; i<mCartList.size(); i++) {

                                Movie curProduct = mCartList.get(i);
                                name += ShoppingCartHelper.getProductQuantity(curProduct) +
                                        " " +mCartList.get(i).getMovieName().toString() + ", ";

                            }

                            for(Movie p : mCartList) {
                                int quantity = ShoppingCartHelper.getProductQuantity(p);
                                subTotal += Integer.parseInt(p.foodPrice) * quantity;
                            }

                            String total = String.valueOf(subTotal);

                            Intent intent = new Intent(ShoppingCartActivity.this, Payment.class);
                            intent.putExtra("order", name);
                            intent.putExtra("total", total);
                            startActivity(intent);
                            finish();

                            for(int i=mCartList.size()-1; i>=0; i--) {
                                Movie m = mCartList.get(i);
                                ShoppingCartHelper.removeProduct(m);
                                mCartList.remove(i);
                            }

                            //Toast.makeText(ShoppingCartActivity.this, name+" "+subTotal, Toast.LENGTH_LONG).show();


                        } // End of onClick(DialogInterface dialog, int whichButton)
                    }); //End of alert.setPositiveButton
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                            dialog.cancel();
                        }
                    }); //End of alert.setNegativeButton
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(ShoppingCartActivity.this);
                    alert.setTitle(Html.fromHtml("<b>No Items in Cart</b>")); //Set Alert dialog title here
                    alert.setMessage("Please select items from the Menu Page");

                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(ShoppingCartActivity.this, MenuActivity.class);
                                startActivity(intent);

                        } // End of onClick(DialogInterface dialog, int whichButton)
                    }); //End of alert.setPositiveButton

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                }


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
                        ShoppingCartHelper.removeProduct(m);

                        mCartList.remove(i);
                    }
                }
                mProductAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the data
        if(mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }

        int subTotal = 0;
        for(Movie p : mCartList) {
            int quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += Integer.parseInt(p.foodPrice) * quantity;
        }

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Subtotal: " + subTotal + " UGX");
    }




}