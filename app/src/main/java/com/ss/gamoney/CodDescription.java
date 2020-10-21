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

public class CodDescription extends AppCompatActivity {
    private String recieveUserId;
    ImageView Tournament_img1,Facebook,Insta;
    TextView description1,Pricedes1;
    DatabaseReference UserRef1;
    Button jointournament;
    String price1,date,month,time,tournament,location,tournamentimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod_description);
        Pricedes1 = findViewById(R.id.priceinput1);

        UserRef1 = FirebaseDatabase.getInstance().getReference().child("Cod Tournaments");
        recieveUserId = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("user_id")).toString();

        Tournament_img1 = findViewById(R.id.image_recycler1);
        description1 = findViewById(R.id.image_description1);
        jointournament = findViewById(R.id.jointournament1);

        RetrieveUserInfo();

        Facebook = findViewById(R.id.facebook1);
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

        Insta = findViewById(R.id.instagram1);
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
                Intent intent = new Intent(CodDescription.this,CodAfterdes.class);
                intent.putExtra("price2",price1);
                intent.putExtra("date2", date);
                intent.putExtra("location2", location);
                intent.putExtra("tournament2", tournament);
                intent.putExtra("month2", month);
                intent.putExtra("time2", time);
                intent.putExtra("img2",tournamentimg);
                startActivity(intent);
            }
        });

    }

    private void RetrieveUserInfo() {
        UserRef1.child(recieveUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.exists()) && (snapshot.hasChild("image"))) {
                    price1 = Objects.requireNonNull(snapshot.child("price").getValue()).toString();
                    date = Objects.requireNonNull(snapshot.child("date").getValue()).toString();
                    location = Objects.requireNonNull(snapshot.child("map").getValue()).toString();
                    tournament = Objects.requireNonNull(snapshot.child("tournament").getValue()).toString();
                    month = Objects.requireNonNull(snapshot.child("month").getValue()).toString();
                    time = Objects.requireNonNull(snapshot.child("time").getValue()).toString();
                    tournamentimg = Objects.requireNonNull(snapshot.child("image").getValue()).toString();
                    String Description1 = Objects.requireNonNull(snapshot.child("description").getValue()).toString();
                    description1.setText(Description1);
                    Pricedes1.setText(price1);
                    Glide.with(CodDescription.this).load(tournamentimg).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).override(200,200).centerCrop().into(Tournament_img1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}