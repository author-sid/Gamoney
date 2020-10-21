package com.ss.gamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FreefireDescription extends AppCompatActivity {
    private String recieveUserId;
    ImageView Tournament_img2, Facebook, Insta;
    TextView description2, pricedes2;
    DatabaseReference UserRef2;
    Button jointournament;
    String price2,date,month,time,tournament,location,tournamentimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freefire_description);
        pricedes2 = findViewById(R.id.priceinput2);

        UserRef2 = FirebaseDatabase.getInstance().getReference().child("Freefire Tournaments");
        recieveUserId = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("user_id")).toString();

        Tournament_img2 = findViewById(R.id.image_recycler2);
        description2 = findViewById(R.id.image_description2);
        jointournament = findViewById(R.id.jointournament2);

        RetrieveUserInfo();

        Facebook = findViewById(R.id.facebook2);
        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/Gamoney-104888134744902");
                Intent facebook = new Intent(Intent.ACTION_VIEW, uri);
                facebook.setPackage("com.facebook.katana");
                try {
                    startActivity(facebook);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Gamoney-104888134744902")));
                }
            }
        });

        Insta = findViewById(R.id.instagram2);
        Insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/gamoney1/");
                Intent instagram = new Intent(Intent.ACTION_VIEW, uri);
                instagram.setPackage("com.instagram.android");
                try {
                    startActivity(instagram);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/gamoney1/")));
                }
            }
        });
        jointournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FreefireDescription.this, FreefireAfterdes.class);
                intent.putExtra("price3", price2);
                intent.putExtra("date3", date);
                intent.putExtra("location3", location);
                intent.putExtra("tournament3", tournament);
                intent.putExtra("month3", month);
                intent.putExtra("time3", time);
                intent.putExtra("img3",tournamentimg);
                startActivity(intent);
            }
        });
    }

    private void RetrieveUserInfo() {
        UserRef2.child(recieveUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.exists()) && (snapshot.hasChild("image"))) {
                    price2 = Objects.requireNonNull(snapshot.child("price").getValue()).toString();
                    date = Objects.requireNonNull(snapshot.child("date").getValue()).toString();
                    location = Objects.requireNonNull(snapshot.child("map").getValue()).toString();
                    tournament = Objects.requireNonNull(snapshot.child("tournament").getValue()).toString();
                    month = Objects.requireNonNull(snapshot.child("month").getValue()).toString();
                    time = Objects.requireNonNull(snapshot.child("time").getValue()).toString();
                    tournamentimg = Objects.requireNonNull(snapshot.child("image").getValue()).toString();
                    String Description2 = Objects.requireNonNull(snapshot.child("description").getValue()).toString();
                    pricedes2.setText(price2);
                    description2.setText(Description2);
                    Glide.with(FreefireDescription.this).load(tournamentimg).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).override(200, 200).centerCrop().into(Tournament_img2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}