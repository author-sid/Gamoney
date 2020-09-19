package com.ss.gamoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CodRecycler extends AppCompatActivity {
    RecyclerView recview;
    adapter_cod adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod_recycler);

        recview = findViewById(R.id.recview1);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model_cod> options =
                new FirebaseRecyclerOptions.Builder<model_cod>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cod Tournaments"), model_cod.class)
                        .build();

        adapter = new adapter_cod(options,getApplicationContext());
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