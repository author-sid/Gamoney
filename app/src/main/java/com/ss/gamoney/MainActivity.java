package com.ss.gamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Profile Selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.tournament:
                        startActivity(new Intent(getApplicationContext(), Tournaments.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        return true;

                    case R.id.faq:
                        startActivity(new Intent(getApplicationContext(), Faq.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.chat:
                        Toast.makeText(getApplicationContext(), "ComingSoon!", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });

    }
}