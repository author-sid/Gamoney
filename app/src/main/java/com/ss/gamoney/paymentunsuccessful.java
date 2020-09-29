package com.ss.gamoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class paymentunsuccessful extends AppCompatActivity {
    private TextView Backtohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentunsuccessful);

        Backtohome = findViewById(R.id.Backtohome);
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