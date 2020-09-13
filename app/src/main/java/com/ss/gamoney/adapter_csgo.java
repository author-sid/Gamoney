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

public class adapter_csgo extends FirebaseRecyclerAdapter<model_csgo,adapter_csgo.csgoholder> {

    public adapter_csgo(@NonNull FirebaseRecyclerOptions<model_csgo> options) {
        super(options);
    }




    @Override
    protected void onBindViewHolder(@NonNull csgoholder holder, int position, @NonNull model_csgo model_csgo) {
        holder.price.setText(model_csgo.getPrice());
        holder.prize.setText(model_csgo.getPrize());
        holder.description.setText(model_csgo.getDescription());
        holder.time.setText(model_csgo.getTime());
        Glide.with(holder.img.getContext()).load(model_csgo.getImage()).into(holder.img);

    }

    @NonNull
    @Override
    public csgoholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_cardview,parent,false);
        return new csgoholder(view);
    }

    class csgoholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView price, prize, description, time;

        public csgoholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.Tournament_image);
            price = itemView.findViewById(R.id.Tournament_price);
            prize = itemView.findViewById(R.id.Tournament_prize);
            description = itemView.findViewById(R.id.Tournament_description);
            time = itemView.findViewById(R.id.Tournament_time);
        }
    }
}
