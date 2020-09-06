package com.ss.gamoney;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Faq extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        TextView mTitleWindow = (TextView) findViewById(R.id.titleWindow);
        TextView mMessageWindow= (TextView) findViewById(R.id.messageWindow);

        mTitleWindow.setText("FAQ's");

        String Message = "How To Join a Tournament:" +
                "\n " +
                "\n" +
                "1.Select the Game from tournament section you want to play.\n" +
                "2.After selecting Game, Select the tournament type(Different tournament types have different rewards).\n" +
                "3.Register your team correctly (Any changes after registration is not allowed).\n" +
                "4.Pay the desired amount.\n" +
                "5.After successful payment, You will get Match id and password through email and in tournament section, 15 minutes before match.\n" +
                "6.Rewards will be given just after match through desired payment method.\n" +
                "\n Q.Will i always get reward?\n" +
                "A.Yes! if you loose, we will be giving ₹10/kill or depends on tournament type.\n" +
                "\n" +
                "Q.Can i play without team?\n" +
                "A.Yes! You can play without team or team up with random players.\n" +
                "\n" +
                "Q.How will i get reward?\n" +
                "A.You can select your payment option while registering for tournament. \n" +
                "\n" +
                "Q.When will rewards be credited? \n" +
                "A.After the match, Rewards will be transferred immediately with a confirmation email. \n" +
                "\n" +
                "Q.If there is a mistake while registering for the tournament? \n" +
                "A.If you enter wrong name/id of your teammates, You will be disqualified from the match.\n"+
                "\n" +
                "*For any queries feel free to contact us: \n ";

        mMessageWindow.setText(Message);

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);

        //Set Faq Selected
        bottomNavigationView.setSelectedItemId(R.id.faq);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.tournament:
                        startActivity(new Intent(getApplicationContext(), Tournaments.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.faq:
                        return true;

                    case R.id.chat:
                        Toast.makeText(getApplicationContext(),"ComingSoon!",Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });
    }
}