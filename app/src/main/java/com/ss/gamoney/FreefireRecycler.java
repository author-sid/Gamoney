package com.ss.gamoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FreefireRecycler extends AppCompatActivity {
    RecyclerView recview;
    adapter_freefire adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freefire_recycler);

        recview = findViewById(R.id.recview2);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model_freefire> options =
                new FirebaseRecyclerOptions.Builder<model_freefire>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Freefire Tournaments"), model_freefire.class)
                        .build();

        adapter = new adapter_freefire(options,getApplicationContext());
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