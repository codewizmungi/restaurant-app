package com.ucu.twentyfourseven.shopping_cart;

import java.io.InputStream;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucu.twentyfourseven.R;
import com.ucu.twentyfourseven.menu.Movie;

public class MenuAdapter extends BaseAdapter {

    private List<Movie> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowQuantity;
    private boolean mShowCheckbox;

    public MenuAdapter(List<Movie> list, LayoutInflater inflater, boolean showQuantity, boolean showCheckbox) {
        mProductList = list;
        mInflater = inflater;
        mShowQuantity = showQuantity;
        mShowCheckbox = showCheckbox;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.itemcart, null);
            item = new ViewItem();

            item.productImageView = (ImageView) convertView
                    .findViewById(R.id.ImageViewItem);

            item.productTitle = (TextView) convertView
                    .findViewById(R.id.TextViewItem);

            item.productQuantity = (TextView) convertView
                    .findViewById(R.id.textViewQuantity);

            item.productCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelected);

            convertView.setTag(item);

        } else {
            item = (ViewItem) convertView.getTag();
        }

        Movie curProduct = mProductList.get(position);

        new DownloadImageTask(item.productImageView)
               .execute(curProduct.imageLink);

        //itemcart.productImageView.setImageDrawable(curProduct.productImage);
        item.productTitle.setText(curProduct.movieName);

        // Show the quantity in the cart or not
        if (mShowQuantity) {
            item.productQuantity.setText("Quantity: "
                    + ShoppingCartHelper.getProductQuantity(curProduct));
        } else {
            // Hid the view
            item.productQuantity.setVisibility(View.GONE);
        }

        if(!mShowCheckbox) {
            item.productCheckbox.setVisibility(View.GONE);
        } else {
            if(curProduct.selected == true)
                item.productCheckbox.setChecked(true);
            else
                item.productCheckbox.setChecked(false);
        }

        return convertView;
    }

    private class ViewItem {
        ImageView productImageView;
        TextView productTitle;
        TextView productQuantity;
        CheckBox productCheckbox;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}