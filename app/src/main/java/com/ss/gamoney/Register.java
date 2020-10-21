package com.ss.gamoney;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn, mLoginBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;
    ProgressDialog progressDialog;
    Spanned mymesage;
    String message = "By using our app You agree to our <a href=\"https://github.com/author-sid/Gamoney-Privacy-policy/blob/master/privacy%20policy.txt\"> Privacy Policy</a> \n <a href=\"https://github.com/author-sid/Gamoney-Privacy-policy/blob/master/Terms%20of%20condition.txt\"> Terms and Condition</a>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phnNo);
        mRegisterBtn = findViewById(R.id.registerbtn);
        mLoginBtn = findViewById(R.id.alreadybtn);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        mymesage = Html.fromHtml(message);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String Fullname= mFullName.getText().toString();
                final String phone = mPhone.getText().toString();

                if(TextUtils.isEmpty(Fullname)){
                    mFullName.setError("Name is Required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be 6 Characters long");
                    return;
                }

                if (Fullname.length() < 5){
                    Toast.makeText(Register.this,"Name must be at least 5 characters long",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.length() != 10){
                    mPhone.setError("Phone number must be 10 digits");
                    return;
                }

                if (TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone Number is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password cannot be empty");
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setTitle("Agree To Terms and Condition");
                builder.setMessage(mymesage);
                builder.setCancelable(false);
                builder.setPositiveButton("I Agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = new ProgressDialog(Register.this);
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        progressDialog.setContentView(R.layout.activity_progress_dialog);
                        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                        //register the user in firebase

                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    //send verification link
                                    FirebaseUser fuser = mAuth.getCurrentUser();
                                    assert fuser != null;
                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Register.this,"Verfication Email Has been Sent",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"On Failure : Email not sent "+ e.getMessage());
                                        }
                                    });

                                    userID = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fstore.collection("users").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("fullName",Fullname);
                                    user.put("email",email);
                                    user.put("phone",phone);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: User Profile is Created for "+ userID);
                                        }
                                    }) .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: "+ e.toString());
                                        }
                                    });
                                    startActivity(new Intent(Register.this,LoginActivity.class));
                                    progressDialog.dismiss();

                                }else{
                                    Toast.makeText(Register.this,"Error!" + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                });
                AlertDialog Alert1 = builder.create();
                Alert1.show();
                TextView msgTxt = Alert1.findViewById(android.R.id.message);
                msgTxt.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}