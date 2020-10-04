package com.ss.gamoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class paymentunsuccessful extends AppCompatActivity {
     TextView Backtohome;
     Button Retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentunsuccessful);

        Backtohome = findViewById(R.id.Backtohome);
        Retry = findViewById(R.id.Retry);
        Backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthome1 = new Intent(paymentunsuccessful.this,Tournaments.class);
                startActivity(intenthome1);
                finish();
            }
        });

    }

}