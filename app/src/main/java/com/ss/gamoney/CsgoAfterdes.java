package com.ss.gamoney;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class CsgoAfterdes extends AppCompatActivity implements PaymentResultListener {
    EditText Player1, Player2, Player3, Player4, Player5, PhoneNo;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    Button payment;
    String userID, priceafter3, username, phonenumber, email, date , time, month, location, tournament,tournamentimg;
    int randomNumber;
    public static final String TAG = "TAG";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csgo_afterdes);
        Player1 = findViewById(R.id.player1input1);
        Player2 = findViewById(R.id.player1input2);
        Player3 = findViewById(R.id.player1input3);
        Player4 = findViewById(R.id.player1input4);
        Player5 = findViewById(R.id.player1input5);
        PhoneNo = findViewById(R.id.phoneinput);
        payment = findViewById(R.id.payment);
        priceafter3 = getIntent().getStringExtra("price4");
        date = getIntent().getStringExtra("date4");
        time = getIntent().getStringExtra("time4");
        month = getIntent().getStringExtra("month4");
        location = getIntent().getStringExtra("location4");
        tournament = getIntent().getStringExtra("tournament4");
        tournamentimg = getIntent().getStringExtra("img4");

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        username = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fstore.collection("users").document(username);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                email = Objects.requireNonNull(value.get("email")).toString();
                phonenumber = Objects.requireNonNull(value.get("phone")).toString();
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Player_1 = Player1.getText().toString();
                final String Player_2 = Player2.getText().toString();
                final String Player_3 = Player3.getText().toString();
                final String Player_4 = Player4.getText().toString();
                final String Player_5 = Player5.getText().toString();
                final String PhoneNumber = PhoneNo.getText().toString();

                if (TextUtils.isEmpty(Player_1)) {
                    Player1.setError("Player information is required");
                    return;
                }

                if (TextUtils.isEmpty(Player_2)) {
                    Player2.setError("Player information is required");
                    return;
                }

                if (TextUtils.isEmpty(Player_3)) {
                    Player3.setError("Player information is required");
                    return;
                }

                if (TextUtils.isEmpty(Player_4)) {
                    Player4.setError("Player information is required");
                    return;
                }

                if (TextUtils.isEmpty(Player_5)) {
                    Player5.setError("Player information is required");
                    return;
                }

                if (TextUtils.isEmpty(PhoneNumber)) {
                    PhoneNo.setError("Phone number is required");
                    return;
                }
                if (PhoneNumber.length() != 10) {
                    PhoneNo.setError("Please Enter Valid Phone number");
                    return;
                }

                if (TextUtils.isEmpty(PhoneNumber)) {
                    PhoneNo.setError("Phone number is required");
                    return;
                }

                progressDialog = new ProgressDialog(CsgoAfterdes.this);
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setContentView(R.layout.activity_progress_dialog);
                Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fstore.collection("users").document(userID).collection("Team Info").document("CsgoTeam");
                Map<String, Object> user = new HashMap<>();
                user.put("CSGOP1", Player_1);
                user.put("CSGOP2", Player_2);
                user.put("CSGOP3", Player_3);
                user.put("CSGOP4", Player_4);
                user.put("CSGOP5", Player_5);
                user.put("PrizeNumber", PhoneNumber);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Team information Added Successfully" + userID);
                        startPayment();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
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
            double total = Double.parseDouble(priceafter3);
            total = total * 100;
            options.put("amount", total);//pass amount in currency subunits

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", phonenumber);
            options.put("prefill", preFill);
            co.open(activity, options);

        } catch (Exception e) {
            Log.e("error", "error" + e.toString());
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        progressDialog = new ProgressDialog(CsgoAfterdes.this);
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
        map.put("price",priceafter3);
        documentReference.set(map);
        Intent intent = new Intent(CsgoAfterdes.this,Paymentsuccesssfull.class);
        intent.putExtra("Referenceno",randomNumber);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        progressDialog = new ProgressDialog(CsgoAfterdes.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_progress_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Intent intent = new Intent(CsgoAfterdes.this,paymentunsuccessful.class);
        intent.putExtra("Referencenoun",randomNumber);
        intent.putExtra("price",priceafter3);
        intent.putExtra("email",email);
        intent.putExtra("phonenumber",phonenumber);
        intent.putExtra("tournament",tournament);
        intent.putExtra("month",month);
        intent.putExtra("date",date);
        intent.putExtra("time",time);
        intent.putExtra("location",location);
        intent.putExtra("image",tournamentimg);
        startActivity(intent);
    }
}