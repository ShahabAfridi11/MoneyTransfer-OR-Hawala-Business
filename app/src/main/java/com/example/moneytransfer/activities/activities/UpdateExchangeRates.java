package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneytransfer.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UpdateExchangeRates extends AppCompatActivity {

    EditText mDhrToPkr, mPkrToDhr, mDollarToPkr, mPkrToDollar, mEuroToPkr , mPkrToEuro;
    Button mUpdateRates;

    DatabaseReference database;
    Bundle mGetValue;

    private String dhrToPkr, pkrToDhr, dollarToPkr,
            pkrToDollar, euroToPkr, pkrToEuro,updatedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exchange_rates);

        init();
        updateExchangeRates();
    }

    private void updateExchangeRates() {

        mUpdateRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhrToPkr = mDhrToPkr.getText().toString().trim();
                pkrToDhr = mPkrToDhr.getText().toString().trim();
                dollarToPkr = mDollarToPkr.getText().toString().trim();
                pkrToDollar = mPkrToDollar.getText().toString().trim();
                euroToPkr = mEuroToPkr.getText().toString().trim();
                pkrToEuro = mPkrToEuro.getText().toString().trim();
                updatedOn =new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


                HashMap<String, Object> myData = new HashMap<>();
                myData.put("dhrtopkr", pkrToDhr);
                myData.put("pkrtodhr", pkrToDhr);
                myData.put("dollartopkr", dollarToPkr);
                myData.put("pkrtodollar", pkrToDollar);
                myData.put("eurotopkr", euroToPkr);
                myData.put("pkrtoeuro", pkrToEuro);
                myData.put("udateddate", updatedOn);

                FirebaseDatabase.getInstance().getReference().child("exchangerates").updateChildren(myData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateExchangeRates.this, "Rates Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateExchangeRates.this, AdminHome.class);
                                intent.putExtra("name",mGetValue.getString("name"));
                                intent.putExtra("useremail",mGetValue.getString("useremail"));
                                intent.putExtra("fathername",mGetValue.getString("fathername"));
                                intent.putExtra("cnicnumber",mGetValue.getString("cnicnumber"));
                                intent.putExtra("cnicpath",mGetValue.getString("cnicpath"));
                                intent.putExtra("profilepic",mGetValue.getString("profilepic"));
                                intent.putExtra("phonenumber",mGetValue.getString("phonenumber"));
                                intent.putExtra("date",mGetValue.getString("date"));
                                intent.putExtra("password", mGetValue.getString("password"));
                                intent.putExtra("branch",mGetValue.getString("branch"));
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(UpdateExchangeRates.this, "Faild to Updated Rates", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void init() {
        mGetValue = getIntent().getExtras();
        mDhrToPkr = findViewById(R.id.dhr_to_pkr_et);
        mPkrToDhr = findViewById(R.id.pkr_to_dhr_et);
        mDollarToPkr = findViewById(R.id.dollar_to_pkr_et);
        mPkrToDollar = findViewById(R.id.pkr_to_dollar_et);
        mEuroToPkr = findViewById(R.id.euro_to_pkr_et);
        mPkrToEuro= findViewById(R.id.pkr_to_euro_et);
        database = FirebaseDatabase.getInstance().getReference();
        mUpdateRates = findViewById(R.id.update_rates_btn);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateExchangeRates.this, AdminHome.class);
        intent.putExtra("name",mGetValue.getString("name"));
        intent.putExtra("useremail",mGetValue.getString("useremail"));
        intent.putExtra("fathername",mGetValue.getString("fathername"));
        intent.putExtra("cnicnumber",mGetValue.getString("cnicnumber"));
        intent.putExtra("cnicpath",mGetValue.getString("cnicpath"));
        intent.putExtra("profilepic",mGetValue.getString("profilepic"));
        intent.putExtra("phonenumber",mGetValue.getString("phonenumber"));
        intent.putExtra("date",mGetValue.getString("date"));
        intent.putExtra("password", mGetValue.getString("password"));
        intent.putExtra("branch",mGetValue.getString("branch"));
        startActivity(intent);
        finish();
    }
}