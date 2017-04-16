package com.ucu.twentyfourseven;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ucu.twentyfourseven.menu.MenuActivity;

import java.util.concurrent.TimeUnit;

public class ConfirmDetails extends AppCompatActivity {

    TextView text1;

    private static final String FORMAT = "%02d:%02d:%02d";

    int seconds , minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);

        text1=(TextView)findViewById(R.id.timer);

        new CountDownTimer(1800100, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                text1.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                Intent intent = new Intent(ConfirmDetails.this, MenuActivity.class);
                startActivity(intent);
            }
        }.start();

    }
}
