package com.ss.gamoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Paymentsuccesssfull extends AppCompatActivity {
    private TextView Backtohome2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentsuccesssfull);

        Backtohome2 = findViewById(R.id.Backtohome2);
        Backtohome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthome2 = new Intent(Paymentsuccesssfull.this,Tournaments.class);
                startActivity(intenthome2);
                finish();
            }
        });
    }
}