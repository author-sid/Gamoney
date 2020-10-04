package com.ss.gamoney;

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

public class adapter_freefire extends FirebaseRecyclerAdapter<model_freefire,adapter_freefire.freefireholder> {
    Context context;
    public adapter_freefire(@NonNull FirebaseRecyclerOptions<model_freefire> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull freefireholder holder, final int position, @NonNull model_freefire model_freefire) {
        holder.tournament.setText(model_freefire.getTournament());
        holder.date.setText(model_freefire.getDate());
        holder.month.setText(model_freefire.getMonth());
        holder.map.setText(model_freefire.getMap());
        holder.price.setText(model_freefire.getPrice());
        holder.time.setText(model_freefire.getTime());
        Glide.with(holder.img.getContext()).load(model_freefire.getImage()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = getRef(position).getKey();
                Intent profileIntent = new Intent(context, FreefireDescription.class);
                profileIntent.putExtra("user_id", user_id);
                profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(profileIntent);
            }
        });
    }

    @NonNull
    @Override
    public freefireholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_cardview,parent,false);
        return new freefireholder(view);
    }

    static class freefireholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView price, time, date , map , month , tournament;
        public freefireholder(@NonNull View itemView) {
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
