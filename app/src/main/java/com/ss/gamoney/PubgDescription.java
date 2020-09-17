package com.ss.gamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
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
    ImageView Tournament_img;
    TextView description;
    DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubg_description);

        UserRef = FirebaseDatabase.getInstance().getReference().child("Pubg Tournaments");
        receiverUserID = getIntent().getExtras().get("visit_user_id").toString();

        Tournament_img = findViewById(R.id.image_recycler);
        description = findViewById(R.id.image_description);
        
        RetrieveUserInfo();
    }

    private void RetrieveUserInfo() {
        UserRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.exists()) && (snapshot.hasChild("image"))){
                    String tournamentimg = snapshot.child("image").getValue().toString();
                    String Description = snapshot.child("description").getValue().toString();
                    description.setText(Description);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}