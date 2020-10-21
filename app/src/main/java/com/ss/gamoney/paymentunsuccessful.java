package com.ss.gamoney;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.razorpay.PaymentResultListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class paymentunsuccessful extends AppCompatActivity implements PaymentResultListener{
     TextView Backtohome,referencenumber;
     Button Retry;
     String email,phoneno,price,referenceno,userID,tournament,month,time,tournamentimg,location,date;
     int randomNumber;
     ProgressDialog progressDialog;
     FirebaseAuth mAuth;
     FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentunsuccessful);

        Backtohome = findViewById(R.id.Backtohome);
        Retry = findViewById(R.id.Retry);
        email = getIntent().getStringExtra("email");
        phoneno = getIntent().getStringExtra("phonenumber");
        price = getIntent().getStringExtra("price");
        referenceno = getIntent().getStringExtra("Referencenoun");
        tournament = getIntent().getStringExtra("tournament");
        month = getIntent().getStringExtra("month");
        time = getIntent().getStringExtra("time");
        tournamentimg = getIntent().getStringExtra("image");
        location = getIntent().getStringExtra("location");
        date = getIntent().getStringExtra("date");
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        referencenumber.setText(referenceno);

        Backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthome1 = new Intent(paymentunsuccessful.this,Tournaments.class);
                startActivity(intenthome1);
                finish();
            }
        });

        Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }

    public void startPayment() {
        Activity activity = this;
        Checkout co = new Checkout();
        Random random = new Random();
        randomNumber = random.nextInt(123450 - 12340 +1)+12340;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Gamoney");
            options.put("description", "Reference No. #"+randomNumber);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#da3f3d");
            options.put("currency", "INR");
            double total = Double.parseDouble(price);
            total = total * 100;
            options.put("amount", total);//pass amount in currency subunits

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", phoneno);
            options.put("prefill", preFill);
            co.open(activity, options);

        } catch (Exception e) {
            Log.e("error", "error" + e.toString());
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        progressDialog = new android.app.ProgressDialog(paymentunsuccessful.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_progress_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        mAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fstore.collection("users").document(userID).collection("Joined").document();
        Map<String, Object> map = new HashMap<>();
        map.put("tournament", tournament);
        map.put("month", month);
        map.put("location", location);
        map.put("date", date);
        map.put("time", time);
        map.put("image",tournamentimg);
        map.put("price",price);
        documentReference.set(map);
        Intent intent = new Intent(paymentunsuccessful.this,Paymentsuccesssfull.class);
        intent.putExtra("Referenceno",randomNumber);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        progressDialog = new ProgressDialog(paymentunsuccessful.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_progress_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Intent intent = new Intent(paymentunsuccessful.this,paymentunsuccessful.class);
        intent.putExtra("Referencenoun",randomNumber);
        intent.putExtra("price",price);
        intent.putExtra("email",email);
        intent.putExtra("phonenumber",phoneno);
        intent.putExtra("tournament",tournament);
        intent.putExtra("month",month);
        intent.putExtra("date",date);
        intent.putExtra("time",time);
        intent.putExtra("location",location);
        intent.putExtra("image",tournamentimg);
        startActivity(intent);
    }

}