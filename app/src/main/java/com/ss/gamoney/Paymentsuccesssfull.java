package com.ss.gamoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Paymentsuccesssfull extends AppCompatActivity {
    TextView Backtohome2,Referenceno;
    Toolbar toolbar;
    int reference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentsuccesssfull);
        reference = getIntent().getIntExtra("Referenceno",0);
        Referenceno = findViewById(R.id.referencenumber);
        Referenceno.setText(""+reference);
        Backtohome2 = findViewById(R.id.Backtohome2);
        toolbar = findViewById(R.id.toolbar);

        Backtohome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthome2 = new Intent(Paymentsuccesssfull.this,Tournaments.class);
                startActivity(intenthome2);
                finish();
            }
        });

        setSupportActionBar(toolbar);
    }
}