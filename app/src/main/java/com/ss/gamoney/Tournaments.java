package com.ss.gamoney;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Tournaments extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    Button BTN1,BTN2,BTN3,BTN4;
    ProgressDialog progressDialog;
    TextView joined_tournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);

        joined_tournament = findViewById(R.id.joined_tournament);
        joined_tournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentjoinedtournaments = new Intent(Tournaments.this , joinedTournament.class);
                startActivity(intentjoinedtournaments);
                finish();
            }
        });

        BTN1 = findViewById(R.id.BTN1);
        BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent categoryintent = new Intent(Tournaments.this, PubgRecycler.class);
                startActivity(categoryintent);
                finish();
            }
        });

        BTN2 = findViewById(R.id.BTN2);
        BTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent categoryintent = new Intent(Tournaments.this, CodRecycler.class);
                startActivity(categoryintent);
                finish();
            }
        });


        BTN3 = findViewById(R.id.BTN3);
        BTN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent categoryintent = new Intent(Tournaments.this, FreefireRecycler.class);
                startActivity(categoryintent);
                finish();
            }
        });


        BTN4 = findViewById(R.id.BTN4);
        BTN4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent categoryintent = new Intent(Tournaments.this, csgoRecycler.class);
                startActivity(categoryintent);
                finish();
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_tournamentinfo);

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Tournament Selected
        bottomNavigationView.setSelectedItemId(R.id.tournament);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.tournament:
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.faq:
                        startActivity(new Intent(getApplicationContext(), Faq.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.chat:
                        Toast.makeText(getApplicationContext(), "ComingSoon!", Toast.LENGTH_LONG).show();
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
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
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;

            case R.id.nav_Logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(Tournaments.this);
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
                AlertDialog.Builder resetpassword = new AlertDialog.Builder(Tournaments.this);
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
                                    Toast.makeText(Tournaments.this, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
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

            case R.id.joined_tournament:
                Intent intent5 = new Intent(getApplicationContext(), joinedTournament.class);
                startActivity(intent5);
                finish();


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}