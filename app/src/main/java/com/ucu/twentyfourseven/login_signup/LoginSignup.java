package com.ucu.twentyfourseven.login_signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ucu.twentyfourseven.R;

public class LoginSignup extends AppCompatActivity {

    Button loginButton;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        loginButton = (Button) findViewById(R.id.buttonLogin);
        signupButton = (Button) findViewById(R.id.signup_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignup.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignup.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
