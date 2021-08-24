package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytransfer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Exchange extends AppCompatActivity {

    Bundle mGetValue;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Spinner mRates;
    ArrayList<String> mRatesList;
    ValueEventListener mRateValueEventListener;
    ArrayAdapter<String> mRatesAdapter;
    private DatabaseReference databaseReferenceSpinner;

    private TextView mDhrToPkr, mPkrToDhr, mDollarToPkr, mPkrToDollar, mEuroToPkr , mPkrToEuro,mTotalAmount;
    EditText mAmount;
    private Button mExchanged;

    private double rate;
    double formula;
    double total;
    double sendAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        databaseReferenceSpinner = FirebaseDatabase.getInstance().getReference("exchangerates");

        init();
        addExchangeRatesToSpinner();

        getAndSetData();

        mExchanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAmount.getText().equals("")){
                    Toast.makeText(Exchange.this, "Must Enter Amount", Toast.LENGTH_SHORT).show();
                    mAmount.setError("Must Enter Amount");

                }else if(mRates.getSelectedItem().equals("Select Currenct Type")){
                    Toast.makeText(Exchange.this, "Select Currency Type Amount", Toast.LENGTH_SHORT).show();
                } else {
                    //           total = Double.parseDouble(mTotalAmount.getText().toString());
                    sendAmount = Double.valueOf(mAmount.getText().toString());

                    databaseReferenceSpinner = FirebaseDatabase.getInstance().getReference().child("exchangerates");
                    databaseReferenceSpinner.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                            if(snapshot.exists()){
                                //      Toast.makeText(GenerateTransaction.this, snapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
                                if(mRates.getSelectedItem().toString().equals(snapshot.getKey().toString())) {
                                    rate = Double.parseDouble(snapshot.getValue().toString());
                                    formula = sendAmount * rate;
                                    long i = (new Double(formula)).longValue();

//                                    Toast.makeText(Exchange.this, String.valueOf(rate), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(Exchange.this, String.valueOf(formula), Toast.LENGTH_SHORT).show();
                                    mTotalAmount.setText(String.valueOf(i));
                                }

                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    private void init() {
        mGetValue = getIntent().getExtras();
        mDhrToPkr = findViewById(R.id.dhr_to_pkr_et_e);
        mPkrToDhr = findViewById(R.id.pkr_to_dhr_et_e);
        mDollarToPkr = findViewById(R.id.dollar_to_pkr_et_e);
        mPkrToDollar = findViewById(R.id.pkr_to_dollar_et_e);
        mEuroToPkr = findViewById(R.id.euro_to_pkr_et_e);
        mPkrToEuro = findViewById(R.id.pkr_to_euro_et_e);
        mRates = findViewById(R.id.currency_type_spinner_e);
        mAmount = findViewById(R.id.amount_e);
        mTotalAmount = findViewById(R.id.total_amount_e);
        mExchanged = findViewById(R.id.exchanged_btn);
    }

    public void addExchangeRatesToSpinner() {
        //Add Currency Types To Spinner
        mRatesList = new ArrayList<>();
        mRatesList.clear();
        mRatesList.add("Select Currenct Type");
        mRatesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,mRatesList);
        mRates.setAdapter(mRatesAdapter);
        mRatesAdapter.notifyDataSetChanged();

        mRateValueEventListener = databaseReferenceSpinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot myData : snapshot.getChildren()){

                    if (myData.getKey().toString().equals("udateddate")){

                    }else {
                        mRatesList.add(myData.getKey().toString());
                        mRatesAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getAndSetData() {
        databaseReferenceSpinner.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
              if(snapshot.exists()){

                  if(snapshot.getKey().equalsIgnoreCase("dhrtopkr")){
                      mDhrToPkr.setText(snapshot.getValue().toString());
                  }else if(snapshot.getKey().equalsIgnoreCase("pkrtodhr")){
                      mPkrToDhr.setText(snapshot.getValue().toString());
                  }else if(snapshot.getKey().equalsIgnoreCase("dollartopkr")){
                      mDollarToPkr.setText(snapshot.getValue().toString());
                  }else if(snapshot.getKey().equalsIgnoreCase("pkrtodollar")){
                      mPkrToDollar.setText(snapshot.getValue().toString());
                  }else if(snapshot.getKey().equalsIgnoreCase("eurotopkr")){
                      mEuroToPkr.setText(snapshot.getValue().toString());
                  }else if(snapshot.getKey().equalsIgnoreCase("pkrtoeuro")){
                      mPkrToEuro.setText(snapshot.getValue().toString());
                  }
              }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(user.getEmail().equalsIgnoreCase("shahabafridi11@gmail.com")){
            Intent intent = new Intent(Exchange.this, AdminHome.class);
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
        } else {
            Intent intent = new Intent(Exchange.this, Home.class);
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
}