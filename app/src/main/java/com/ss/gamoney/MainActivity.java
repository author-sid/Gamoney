package com.ss.gamoney;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public static final String TAG = "TAG";
    TextView FullNameLabel;
    EditText FullName, PhoneNo, Email;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    String userID;
    FirebaseUser user;
    StorageReference storageReference;
    ImageView profileImage, changeProfileImage;
    Button saveBtn;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    static final int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FullName = findViewById(R.id.full_namefield);
        PhoneNo = findViewById(R.id.phone_field);
        Email = findViewById(R.id.email_field);
        FullNameLabel = findViewById(R.id.full_name);
        profileImage = findViewById(R.id.profile_pic);
        changeProfileImage = findViewById(R.id.change_profile);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();
        saveBtn = findViewById(R.id.update);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_tournamentinfo);


        StorageReference profileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = mStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                PhoneNo.setText(value.getString("phone"));
                FullName.setText(value.getString("fullName"));
                Email.setText(value.getString("email"));
                FullNameLabel.setText(value.getString("fullName"));

            }
        });

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Profile Selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.tournament:
                        startActivity(new Intent(getApplicationContext(), Tournaments.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        return true;

                    case R.id.faq:
                        startActivity(new Intent(getApplicationContext(), Faq.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.chat:
                        Toast.makeText(getApplicationContext(), "ComingSoon!", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("Grant Permission");
                        builder1.setMessage("Read Storage");
                        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE
                                        }, REQUEST_CODE);
                            }
                        });
                        builder1.setNegativeButton("Cancel",null);
                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();
                    }else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                }, REQUEST_CODE);
                    }
                }else {
                    Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGalleryIntent, 1000);
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Fullname = FullName.getText().toString();
                final String phone = PhoneNo.getText().toString();
                if (FullName.getText().toString().isEmpty() || Email.getText().toString().isEmpty() || PhoneNo.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "One or many fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() != 10) {
                    Toast.makeText(MainActivity.this, "Phone number must be of 10 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Fullname.length() < 5) {
                    Toast.makeText(MainActivity.this, "Name must be at least 5 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                final String email = Email.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = mStore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email", email);
                        edited.put("fullName", FullName.getText().toString());
                        edited.put("phone", PhoneNo.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        FirebaseUser fuser = mAuth.getCurrentUser();
                        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Verification Email Has been Sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "On Failure : Email not sent " + e.getMessage());
                            }
                        });
                        Toast.makeText(MainActivity.this, "Email is changed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if ((grantResults.length>0)
            &&(grantResults[0]) == PackageManager.PERMISSION_GRANTED){
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }else {
                Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Uri imageUri = data.getData();

                // profileImage.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);

            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.activity_progress_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        //upload image to firebase storage
        final StorageReference fileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                AlertDialog.Builder resetpassword = new AlertDialog.Builder(MainActivity.this);
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
                                    Toast.makeText(MainActivity.this, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}