package com.ss.gamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class joinedTournament extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView joinedtournaments;
    FirebaseFirestore firebaseFirestore;
    String userID;
    FirebaseAuth mAuth;
    FirestoreRecyclerAdapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_tournament);
        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();

        firebaseFirestore = FirebaseFirestore.getInstance();
        joinedtournaments = findViewById(R.id.joined_tournament);
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_tournamentinfo);

        Query query = firebaseFirestore.collection("users").document(userID).collection("Joined");
        FirestoreRecyclerOptions<joinedtournamentsModel> options = new FirestoreRecyclerOptions.Builder<joinedtournamentsModel>()
                .setQuery(query,joinedtournamentsModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<joinedtournamentsModel, joinedViewHolder>(options) {
            @NonNull
            @Override
            public joinedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paidcardview,parent,false);
                return new joinedViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull joinedViewHolder holder, int position, @NonNull joinedtournamentsModel model) {

                holder.date.setText(model.getDate());
                holder.map.setText(model.getLocation());
                holder.month.setText(model.getMonth());
                holder.tournament.setText(model.getTournament());
                holder.time.setText(model.getTime());
                Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(joinedTournament.this,roomidpasss.class);
                        startActivity(intent);
                    }
                });


            }
        };
        joinedtournaments.setHasFixedSize(true);
        joinedtournaments.setLayoutManager(new LinearLayoutManager(this));
        joinedtournaments.setAdapter(adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        switch (menuitem.getItemId()) {
            case R.id.nav_tournamentinfo:
                break;

            case R.id.nav_Contactus:
                Intent intent1 = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent1);
                break;

            case R.id.nav_Faq:
                Intent intent2 = new Intent(getApplicationContext(), Faq.class);
                startActivity(intent2);
                break;

            case R.id.nav_policy:
                Intent intent3 = new Intent(getApplicationContext(), Policy.class);
                startActivity(intent3);
                break;

            case R.id.nav_Supportus:
                Intent intent4 = new Intent(getApplicationContext(), SupportUs.class);
                startActivity(intent4);
                break;

            case R.id.nav_Logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(joinedTournament.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;

            case R.id.nav_Share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Download This Application Now:- https://play.google.com/store/apps/details?id=com.battlerooms.rooms&hl=en";
                String sharesub = "Gamoney App";

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(shareIntent, "Share Using"));
                break;

            case R.id.nav_Resetpassword:
                final EditText password = new EditText(this);
                AlertDialog.Builder resetpassword = new AlertDialog.Builder(joinedTournament.this);
                resetpassword.setTitle("Reset Password");
                resetpassword.setMessage("Enter your Email");
                resetpassword.setView(password);
                resetpassword.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = password.getText().toString();
                        if (mail.matches("")) {
                            Toast.makeText(getApplicationContext(), "Please enter Your email", Toast.LENGTH_SHORT).show();
                        } else {
                            mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(joinedTournament.this, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
                                }
                            });
                        }
                    }
                });
                resetpassword.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                resetpassword.show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class joinedViewHolder extends RecyclerView.ViewHolder{
        TextView date,map,month,tournament,time;
        ImageView image;

        public joinedViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.paidTournament_image);
            date = itemView.findViewById(R.id.paiddate);
            map = itemView.findViewById(R.id.paidmap);
            month = itemView.findViewById(R.id.paidmonth);
            tournament = itemView.findViewById(R.id.paidtournamentname);
            time = itemView.findViewById(R.id.paidtime);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}