package com.ucu.twentyfourseven;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.ucu.twentyfourseven.login_signup.SignupActivity;
import com.ucu.twentyfourseven.menu.Movie;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartActivity;
import com.ucu.twentyfourseven.shopping_cart.ShoppingCartHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Payment extends AppCompatActivity {

    EditText personName, personContact, personAddress;
    String per_name, per_contact, per_address;

    String order;
    String total;

    String DataParseUrl = "http://chakusoza.com/devs/ucu/order.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        personName = (EditText) findViewById(R.id.editTextname);
        personContact = (EditText) findViewById(R.id.editTextphone);
        personAddress = (EditText) findViewById(R.id.editTextaddress);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order = extras.getString("order");
            total = extras.getString("total");;

        }

        Button makePayment = (Button) findViewById(R.id.button);

        // add PhoneStateListener for monitoring
        MyPhoneListener phoneListener = new MyPhoneListener();
        TelephonyManager telephonyManager =
                (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        // receive notifications of telephony state changes
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

        makePayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    // set the data %23 represents # hash sign
                    String uri = "tel:*165*1%23";
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                    startActivity(callIntent);

                    per_name = personName.getText().toString();
                    per_contact = personContact.getText().toString();
                    per_address = personAddress.getText().toString();

                    SendDataToServer(order, total, per_name, per_contact, per_address);

                    AlertDialog.Builder alert = new AlertDialog.Builder(Payment.this);
                    alert.setTitle(Html.fromHtml("<b>Processing Order</b>")); //Set Alert dialog title here
                    alert.setMessage(Html.fromHtml("We are processing Your order!<br/> Please be patient"));

                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            Intent intent = new Intent(Payment.this, ConfirmDetails.class);
                            startActivity(intent);
                            finish();


                        } // End of onClick(DialogInterface dialog, int whichButton)
                    }); //End of alert.setPositiveButton
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(),"Your call has failed...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        TextView orderText = (TextView) findViewById(R.id.order);
        orderText.setText(order);

        TextView totalText = (TextView) findViewById(R.id.Total);
        totalText.setText(total+" UGX");

    }

    private class MyPhoneListener extends PhoneStateListener {

        private boolean onCall = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // phone ringing...
                    Toast.makeText(Payment.this, incomingNumber + " calls you",
                            Toast.LENGTH_LONG).show();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // one call exists that is dialing, active, or on hold
                    Toast.makeText(Payment.this, "on call...",
                            Toast.LENGTH_LONG).show();
                    //because user answers the incoming call
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    // in initialization of the class and at the end of phone call

                    // detect flag from CALL_STATE_OFFHOOK
                    if (onCall == true) {
                        Toast.makeText(Payment.this, "restart app after call",
                                Toast.LENGTH_LONG).show();

                        // restart our application
                        Intent restart = getBaseContext().getPackageManager().
                                getLaunchIntentForPackage(getBaseContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(restart);

                        onCall = false;
                    }
                    break;
                default:
                    break;
            }

        }
    }

    public void SendDataToServer(final String order, final String amount, final String name, final String phone, final String address){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String order_items = order ;
                String order_amount = amount ;
                String pers_name = name ;
                String pers_phone = phone ;
                String pers_address = address ;


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("order_items", order_items));
                nameValuePairs.add(new BasicNameValuePair("order_amount", order_amount));
                nameValuePairs.add(new BasicNameValuePair("personName", pers_name));
                nameValuePairs.add(new BasicNameValuePair("personPhone", pers_phone));
                nameValuePairs.add(new BasicNameValuePair("personAddress", pers_address));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(DataParseUrl);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submit Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(order, amount, name, phone, address);
    }


}
