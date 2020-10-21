package com.ss.gamoney;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Policy extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    Button PrivacyPolicy, Terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        mAuth=FirebaseAuth.getInstance();

        PrivacyPolicy = findViewById(R.id.privacypolicy);
        Terms = findViewById(R.id.terms);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/author-sid/Gamoney-Privacy-policy/blob/master/privacy%20policy.txt")));
            }
        });

        Terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/author-sid/Gamoney-Privacy-policy/blob/master/Terms%20of%20condition.txt")));
            }
        });

        navigationView.bringToFront();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_policy);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        switch (menuitem.getItemId()){
            case R.id.nav_tournamentinfo:
                Intent intent4 = new Intent(getApplicationContext(),Tournaments.class);
                startActivity(intent4);
                break;

            case R.id.nav_Contactus:
                Intent intent1 = new Intent(getApplicationContext(),ContactUs.class);
                startActivity(intent1);
                break;

            case R.id.nav_Faq:
                Intent intent2 = new Intent(getApplicationContext(),Faq.class);
                startActivity(intent2);
                break;

            case R.id.nav_policy:
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Policy.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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
                AlertDialog.Builder resetpassword = new AlertDialog.Builder(Policy.this);
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
                                    Toast.makeText(Policy.this, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
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