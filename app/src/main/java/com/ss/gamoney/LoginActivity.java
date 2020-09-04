package com.ss.gamoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button callSignUp, mLoginBtn, forgotPass;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout mEmail,mPassword;
    ProgressBar progressBar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signup);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        mEmail = findViewById(R.id.email_main);
        mPassword = findViewById(R.id.password_main);
        mLoginBtn = findViewById(R.id.Login_btn);
        progressBar = findViewById(R.id.progressBar2);
        forgotPass = findViewById(R.id.forgotPassword);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getEditText().getText().toString().trim();
                String password = mPassword.getEditText().getText().toString().trim();

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


                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            assert user != null;
                            if(user.isEmailVerified()){
                                Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else {
                                Toast.makeText(LoginActivity.this,"Please Verify Your Email",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }else {
                            Toast.makeText(LoginActivity.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send reset link

                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this,"Reset Link Sent to Your Email",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Error! Reset Link is Not Sent" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close the Dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                Pair[] pairs=new Pair[7];

                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logoText,"logo_text");
                pairs[2] = new Pair<View,String>(sloganText,"logo_desc");
                pairs[3] = new Pair<View,String>(mEmail,"email_trans");
                pairs[4] = new Pair<View,String>(mPassword,"password_trans");
                pairs[5] = new Pair<View,String>(mLoginBtn,"button_trans");
                pairs[6] = new Pair<View,String>(callSignUp,"login_signup_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });
    }
    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this,"Please sign in to continue.",Toast.LENGTH_SHORT).show();
        }
    }
}