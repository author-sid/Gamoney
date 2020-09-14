package com.ss.gamoney;

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

public class adapter_pubg extends FirebaseRecyclerAdapter<model_pubg,adapter_pubg.pubgviewholder> {

    public adapter_pubg(@NonNull FirebaseRecyclerOptions<model_pubg> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull pubgviewholder holder, int position, @NonNull model_pubg model_pubg) {
        holder.price.setText(model_pubg.getPrice());
        holder.description.setText(model_pubg.getDescription());
        holder.time.setText(model_pubg.getTime());
        Glide.with(holder.img.getContext()).load(model_pubg.getImage()).into(holder.img);

    }

    @NonNull
    @Override
    public pubgviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_cardview,parent,false);
        return new pubgviewholder(view);
    }

    static class pubgviewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView price,description,time;

        public pubgviewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.Tournament_image);
            price = itemView.findViewById(R.id.Tournament_price);
            description = itemView.findViewById(R.id.Tournament_description);
            time = itemView.findViewById(R.id.Tournament_time);
        }
    }
}
