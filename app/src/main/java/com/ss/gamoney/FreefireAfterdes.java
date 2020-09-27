package com.ss.gamoney;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class FreefireAfterdes extends AppCompatActivity implements PaymentResultListener {
    EditText Player1, Player2, Player3, Player4, PhoneNo;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    Button payment;
    String userID, priceafter2, username, phonenumber, email;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freefire_afterdes);
        Player1 = findViewById(R.id.player1input1);
        Player2 = findViewById(R.id.player1input2);
        Player3 = findViewById(R.id.player1input3);
        Player4 = findViewById(R.id.player1input4);
        PhoneNo = findViewById(R.id.phoneinput);
        payment = findViewById(R.id.payment);
        priceafter2 = getIntent().getStringExtra("price3");

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
                final String PhoneNumber = PhoneNo.getText().toString();

                if (TextUtils.isEmpty(Player_1)) {
                    Player1.setError("Player information is required");
                }
                if (TextUtils.isEmpty(PhoneNumber)) {
                    PhoneNo.setError("Phone number is required");
                }
                if (PhoneNumber.length() != 10) {
                    PhoneNo.setError("Please Enter Valid Phone number");
                }
                if (TextUtils.isEmpty(Player_2)) {
                    Player2.setError("Player information is required");
                }

                if (TextUtils.isEmpty(PhoneNumber)) {
                    PhoneNo.setError("Phone number is required");
                }


                userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fstore.collection("users").document(userID).collection("Team Info").document("FreefireTeam");
                Map<String, Object> user = new HashMap<>();
                user.put("PUBGP1", Player_1);
                user.put("PUBGP2", Player_2);
                user.put("PUBGP3", Player_3);
                user.put("PUBGP4", Player_4);
                user.put("PrizeNumber", PhoneNumber);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Team information Added Successfully" + userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
                startPayment();
            }
        });
    }

    public void startPayment() {
        Activity activity = this;
        Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            double total = Double.parseDouble(priceafter2);
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

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}