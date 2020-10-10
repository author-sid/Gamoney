package com.ss.gamoney;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class MainActivity extends AppCompatActivity {
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
    ProgressBar progressBar4;


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
        progressBar4= findViewById(R.id.progressBar4);
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();
        saveBtn = findViewById(R.id.update);


        StorageReference profileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
                loadingDialog.startLoadingDialog();
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
                loadingDialog.startLoadingDialog();

            }
        });

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Profile Selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.tournament:
                        startActivity(new Intent(getApplicationContext(), Tournaments.class));
                        overridePendingTransition(0, 0);
                        loadingDialog.startLoadingDialog();
                        return true;
                    case R.id.profile:
                        loadingDialog.startLoadingDialog();
                        return true;

                    case R.id.faq:
                        startActivity(new Intent(getApplicationContext(), joinedTournament.class));
                        overridePendingTransition(0, 0);
                        loadingDialog.startLoadingDialog();
                        return true;

                    case R.id.chat:
                        Toast.makeText(getApplicationContext(), "ComingSoon!", Toast.LENGTH_SHORT).show();
                        loadingDialog.startLoadingDialog();
                        return true;

                }
                return false;
            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
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
                        loadingDialog.startLoadingDialog();
                        Toast.makeText(MainActivity.this, "Email is changed", Toast.LENGTH_SHORT).show();
                        loadingDialog.startLoadingDialog();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

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
        //upload image to firebase storage
        final StorageReference fileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}