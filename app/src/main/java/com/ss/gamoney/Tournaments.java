package com.ss.gamoney;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class Tournaments extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    ImageView pubgtour , csgotour , codtour , freefiretour ;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference1,databaseReference2,databaseReference3,databaseReference4;
    int PUBGtournamentcount, CODtournamentcount, FreeFiretournamentcount, CSGOtournamentcount;
    TextView PUBGtourcount, CODtourcount , Freefiretourcount , CSGotourcount;
    public static final  String CHANNEL_ID = "general";
    public static final String CHANNEL_NAME = "allusers";
    public static final String CHANNEL_DESC = "generalnotifications";
    InterstitialAd pubgtournamentad,codtournamentad,csgotournamentad,freefiretournamentad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);
        PUBGtourcount = findViewById(R.id.pubgtournamentcount);
        CODtourcount = findViewById(R.id.codtournamentcount);
        Freefiretourcount = findViewById(R.id.freefirecount);
        CSGotourcount = findViewById(R.id.csgotournamentcount);

        pubgtournamentad = new InterstitialAd(this);
        pubgtournamentad.setAdUnitId("ca-app-pub-3219785931545711/1048940369");
        pubgtournamentad.loadAd(new AdRequest.Builder().build());

        codtournamentad = new InterstitialAd(this);
        codtournamentad.setAdUnitId("ca-app-pub-3219785931545711/1125456824");
        codtournamentad.loadAd(new AdRequest.Builder().build());

        freefiretournamentad = new InterstitialAd(this);
        freefiretournamentad.setAdUnitId("ca-app-pub-3219785931545711/8429054163");
        freefiretournamentad.loadAd(new AdRequest.Builder().build());

        csgotournamentad = new InterstitialAd(this);
        csgotournamentad.setAdUnitId("ca-app-pub-3219785931545711/7890515036");
        csgotournamentad.loadAd(new AdRequest.Builder().build());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general");

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Pubg Tournaments");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Cod Tournaments");
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Freefire Tournaments");
        databaseReference4 = FirebaseDatabase.getInstance().getReference().child("CSGO Tournaments");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    PUBGtournamentcount = (int) snapshot.getChildrenCount();
                    PUBGtourcount.setText(""+PUBGtournamentcount);
                }else {
                    PUBGtourcount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    CODtournamentcount = (int) snapshot.getChildrenCount();
                    CODtourcount.setText(""+CODtournamentcount);
                }else {
                    CODtourcount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    FreeFiretournamentcount = (int) snapshot.getChildrenCount();
                    Freefiretourcount.setText(""+FreeFiretournamentcount);
                }else{
                    Freefiretourcount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference4.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    CSGOtournamentcount = (int) snapshot.getChildrenCount();
                    CSGotourcount.setText(""+CSGOtournamentcount);
                }else {
                    CSGotourcount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       pubgtour = findViewById(R.id.pubgtour);
       pubgtour.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (pubgtournamentad.isLoaded()){
                   pubgtournamentad.show();
               }
           }
       });

        csgotour = findViewById(R.id.csgotour);
        csgotour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (csgotournamentad.isLoaded()){
                    csgotournamentad.show();
                }
            }
        });

        codtour = findViewById(R.id.codtour);
        codtour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codtournamentad.isLoaded()){
                    codtournamentad.show();
                }
            }
        });

        freefiretour = findViewById(R.id.freefiretour);
        freefiretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (freefiretournamentad.isLoaded()){
                    freefiretournamentad.show();
                }
            }
        });

        pubgtournamentad.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent intentpubgtour = new Intent(Tournaments.this , PubgRecycler.class);
                startActivity(intentpubgtour);
                finish();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent intentpubgtour = new Intent(Tournaments.this , PubgRecycler.class);
                startActivity(intentpubgtour);
                finish();
            }
        });

        codtournamentad.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent intentcodtour = new Intent(Tournaments.this , CodRecycler.class);
                startActivity(intentcodtour);
                finish();
            }

            @Override
            public void onAdClosed() {
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent intentcodtour = new Intent(Tournaments.this , CodRecycler.class);
                startActivity(intentcodtour);
                finish();
            }
        });

        csgotournamentad.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent intentcsgotour = new Intent(Tournaments.this , csgoRecycler.class);
                startActivity(intentcsgotour);
                finish();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent intentcsgotour = new Intent(Tournaments.this , csgoRecycler.class);
                startActivity(intentcsgotour);
                finish();
            }
        });

        freefiretournamentad.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent intentfreefiretour = new Intent(Tournaments.this , FreefireRecycler.class);
                startActivity(intentfreefiretour);
                finish();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                progressDialog = new ProgressDialog(Tournaments.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                Intent intentfreefiretour = new Intent(Tournaments.this , FreefireRecycler.class);
                startActivity(intentfreefiretour);
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
                Intent intent6 = new Intent(getApplicationContext(),Tournaments.class);
                startActivity(intent6);
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
