package com.ss.gamoney;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

public class adapter_cod extends FirebaseRecyclerAdapter<model_cod,adapter_cod.codholder> {
    Context context;
    ProgressDialog progressDialog;

    public adapter_cod(@NonNull FirebaseRecyclerOptions<model_cod> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull codholder holder, final int position, @NonNull model_cod model_cod) {
        progressDialog = new ProgressDialog(context);
        holder.tournament.setText(model_cod.getTournament());
        holder.date.setText(model_cod.getDate());
        holder.month.setText(model_cod.getMonth());
        holder.map.setText(model_cod.getMap());
        holder.price.setText(model_cod.getPrice());
        holder.time.setText(model_cod.getTime());
        Glide.with(holder.img.getContext()).load(model_cod.getImage()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                String user_id = getRef(position).getKey();
                Intent profileIntent = new Intent(context,CodDescription.class);
                profileIntent.putExtra("user_id",user_id);
                profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(profileIntent);
            }
        });
    }

    @NonNull
    @Override
    public codholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_cardview,parent,false);
        return new codholder(view);
    }

    static class codholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView price, time, date , map , month , tournament;
        public codholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            map = itemView.findViewById(R.id.map);
            month = itemView.findViewById(R.id.month);
            tournament = itemView.findViewById(R.id.tournamentname);
            img = itemView.findViewById(R.id.Tournament_image);
            price = itemView.findViewById(R.id.Tournament_price);
            time = itemView.findViewById(R.id.Tournament_time);
        }
    }
}
