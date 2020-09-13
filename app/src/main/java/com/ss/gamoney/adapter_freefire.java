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

public class adapter_freefire extends FirebaseRecyclerAdapter<model_freefire,adapter_freefire.freefireholder> {

    public adapter_freefire(@NonNull FirebaseRecyclerOptions<model_freefire> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull freefireholder holder, int position, @NonNull model_freefire model_freefire) {
        holder.price.setText(model_freefire.getPrice());
        holder.prize.setText(model_freefire.getPrize());
        holder.description.setText(model_freefire.getDescription());
        holder.time.setText(model_freefire.getTime());
        Glide.with(holder.img.getContext()).load(model_freefire.getImage()).into(holder.img);
    }

    @NonNull
    @Override
    public freefireholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_cardview,parent,false);
        return new freefireholder(view);
    }

    static class freefireholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView price,prize,description,time;
        public freefireholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.Tournament_image);
            price = itemView.findViewById(R.id.Tournament_price);
            prize = itemView.findViewById(R.id.Tournament_prize);
            description = itemView.findViewById(R.id.Tournament_description);
            time = itemView.findViewById(R.id.Tournament_time);
        }
    }
}
