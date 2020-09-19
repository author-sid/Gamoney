package com.ss.gamoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class csgoRecycler extends AppCompatActivity {
    RecyclerView recview;
    adapter_csgo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csgo_recycler);

        recview = findViewById(R.id.recview3);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model_csgo> options =
                new FirebaseRecyclerOptions.Builder<model_csgo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("CSGO Tournaments"), model_csgo.class)
                        .build();

        adapter = new adapter_csgo(options,getApplicationContext());
        recview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}