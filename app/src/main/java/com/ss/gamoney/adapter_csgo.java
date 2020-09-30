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

public class adapter_csgo extends FirebaseRecyclerAdapter<model_csgo,adapter_csgo.csgoholder> {
    Context context;

    public adapter_csgo(@NonNull FirebaseRecyclerOptions<model_csgo> options,Context context) {
        super(options);
        this.context = context;
    }




    @Override
    protected void onBindViewHolder(@NonNull csgoholder holder, final int position, @NonNull model_csgo model_csgo) {
        holder.tournamentname.setText(model_csgo.getTournamentname());
        holder.date.setText(model_csgo.getDate());
        holder.month.setText(model_csgo.getMonth());
        holder.map.setText(model_csgo.getMap());
        holder.price.setText(model_csgo.getPrice());
        holder.time.setText(model_csgo.getTime());
        Glide.with(holder.img.getContext()).load(model_csgo.getImage()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = getRef(position).getKey();
                Intent profileIntent = new Intent(context,CsgoDescription.class);
                profileIntent.putExtra("user_id",user_id);
                profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(profileIntent);
            }
        });

    }

    @NonNull
    @Override
    public csgoholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_cardview,parent,false);
        return new csgoholder(view);
    }

    static class csgoholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView price, time, date , map , month , tournamentname;

        public csgoholder(@NonNull View itemView) {
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
