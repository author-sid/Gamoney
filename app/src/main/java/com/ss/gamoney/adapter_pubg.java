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

public class adapter_pubg extends FirebaseRecyclerAdapter<model_pubg, adapter_pubg.pubgviewholder> {

    Context context;

    public adapter_pubg(@NonNull FirebaseRecyclerOptions<model_pubg> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull pubgviewholder holder, final int position, @NonNull model_pubg model_pubg) {
        holder.tournamentname.setText(model_pubg.getTournamentname());
        holder.date.setText(model_pubg.getDate());
        holder.month.setText(model_pubg.getMonth());
        holder.map.setText(model_pubg.getMap());
        holder.price.setText(model_pubg.getPrice());
        holder.time.setText(model_pubg.getTime());
        Glide.with(holder.img.getContext()).load(model_pubg.getImage()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String visit_user_id = getRef(position).getKey();
                Intent profileIntent = new Intent(context, PubgDescription.class);
                profileIntent.putExtra("visit_user_id", visit_user_id);
                profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(profileIntent);
            }
        });


    }

    @NonNull
    @Override
    public pubgviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_cardview, parent, false);
        return new pubgviewholder(view);
    }

    static class pubgviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView price, time, date , map , month , tournamentname;

        public pubgviewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            map = itemView.findViewById(R.id.map);
            month = itemView.findViewById(R.id.month);
            tournamentname = itemView.findViewById(R.id.tournamentname);
            img = itemView.findViewById(R.id.Tournament_image);
            price = itemView.findViewById(R.id.Tournament_price);
            time = itemView.findViewById(R.id.Tournament_time);
        }
    }
}
