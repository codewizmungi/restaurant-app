package com.ucu.twentyfourseven.login_signup;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ucu.twentyfourseven.R;

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

public class SignupActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;

    String GetUsername, GetPassword;

    Button buttonSubmit ;

    String DataParseUrl = "http://chakusoza.com/devs/ucu/signup.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSubmit = (Button)findViewById(R.id.button_Login);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                    GetUsername = editTextUsername.getText().toString();
                    GetPassword = editTextPassword.getText().toString();

                    SendDataToServer(GetUsername, GetPassword);

            }
        });
    }

/*
    public void GetDataFromEditText(){

        GetUsername = editTextUsername.getText().toString();
        GetPassword = editTextPassword.getText().toString();
        GetPassword2 = editTextPassword2.getText().toString();

    }*/


    public void SendDataToServer(final String username, final String password){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String QuickUsername = username ;
                String QuickPassword = password ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("username", QuickUsername));
                nameValuePairs.add(new BasicNameValuePair("password", QuickPassword));

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

                Toast.makeText(SignupActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(username, password);
    }

}
