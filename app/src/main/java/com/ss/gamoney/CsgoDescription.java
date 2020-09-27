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

public class CsgoDescription extends AppCompatActivity {
    private String recieveUserId;
    ImageView Tournament_img3, Insta, Facebook;
    TextView description3, Pricedes3;
    DatabaseReference UserRef3;
    Button jointournament;
    String price3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csgo_description);
        Pricedes3 = findViewById(R.id.priceinput3);

        UserRef3 = FirebaseDatabase.getInstance().getReference().child("CSGO Tournaments");
        recieveUserId = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("user_id")).toString();

        Tournament_img3 = findViewById(R.id.image_recycler3);
        description3 = findViewById(R.id.image_description3);
        jointournament = findViewById(R.id.jointournament3);

        RetrieveUserInfo();

        Facebook = findViewById(R.id.facebook3);
        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/");
                Intent facebook = new Intent(Intent.ACTION_VIEW, uri);
                facebook.setPackage("com.facebook.katana");
                try {
                    startActivity(facebook);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/")));
                }
            }
        });

        Insta = findViewById(R.id.instagram3);
        Insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/accounts/login/");
                Intent instagram = new Intent(Intent.ACTION_VIEW, uri);
                instagram.setPackage("com.instagram.android");
                try {
                    startActivity(instagram);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/accounts/login/")));
                }
            }
        });

        jointournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CsgoDescription.this, CsgoAfterdes.class);
                intent.putExtra("price4", price3);
                startActivity(intent);
            }
        });
    }

    private void RetrieveUserInfo() {
        UserRef3.child(recieveUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.exists()) && (snapshot.hasChild("image"))) {
                    price3 = Objects.requireNonNull(snapshot.child("price").getValue()).toString();
                    String tournamentimg = Objects.requireNonNull(snapshot.child("image").getValue()).toString();
                    String Description3 = Objects.requireNonNull(snapshot.child("description").getValue()).toString();
                    Pricedes3.setText(price3);
                    description3.setText(Description3);
                    Glide.with(CsgoDescription.this).load(tournamentimg).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).override(200, 200).centerCrop().into(Tournament_img3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}