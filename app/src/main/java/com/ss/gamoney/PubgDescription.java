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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PubgDescription extends AppCompatActivity {
    private String receiverUserID;
    ImageView Tournament_img,Facebook,Insta;
    TextView description;
    DatabaseReference UserRef;
    Button jointournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubg_description);

        UserRef = FirebaseDatabase.getInstance().getReference().child("Pubg Tournaments");
        receiverUserID = getIntent().getExtras().get("visit_user_id").toString();

        Tournament_img = findViewById(R.id.image_recycler);
        description = findViewById(R.id.image_description);
        jointournament = findViewById(R.id.jointournament);
        
        RetrieveUserInfo();

        Facebook = findViewById(R.id.facebook1);
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

        Insta = findViewById(R.id.instagram1);
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
                startActivity(new Intent(PubgDescription.this,PubgAfterdes.class));
            }
        });
    }

    private void RetrieveUserInfo() {
        UserRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.exists()) && (snapshot.hasChild("image"))){
                    String tournamentimg = snapshot.child("image").getValue().toString();
                    String Description = snapshot.child("description").getValue().toString();
                    description.setText(Description);
                    Glide.with(PubgDescription.this).load(tournamentimg).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).override(200,200).centerCrop().into(Tournament_img);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}