package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.adapters.AdminControlAdapter;
import com.example.moneytransfer.activities.adapters.PayTransactionAdapter;
import com.example.moneytransfer.activities.models.PayTransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PayTransaction extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PayTransactionAdapter mPayTransactionAdapter;
    private ArrayList<PayTransactionModel> mPayTransaction;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private Bundle mGetValue;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_transaction);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        init();
        loadFireBaseDate();
        if(mPayTransaction.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(PayTransaction.this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFireBaseDate() {
        mGetValue = getIntent().getExtras();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("newtransaction");

        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();

                    PayTransactionModel payTransactionModel = dataSnapshot.getValue(PayTransactionModel.class);

                    if (mGetValue.getString("useremail").equalsIgnoreCase(user.getEmail())){
                        if(payTransactionModel.getReceivingbranch().equalsIgnoreCase(mGetValue.getString("branch"))){
                            mPayTransaction.add(payTransactionModel);


                            mPayTransactionAdapter = new PayTransactionAdapter(mPayTransaction, PayTransaction.this);
                            mPayTransactionAdapter.notifyDataSetChanged();

                            mrecyclerView.setAdapter(mPayTransactionAdapter);

                        }else {
                            mPayTransaction.add(payTransactionModel);


                            mPayTransactionAdapter = new PayTransactionAdapter(mPayTransaction, PayTransaction.this);
                            mPayTransactionAdapter.notifyDataSetChanged();

                            mrecyclerView.setAdapter(mPayTransactionAdapter);
                        }
                    } else {

                    }


                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                progressDialog.dismiss();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    private void init() {
        mrecyclerView = findViewById(R.id.pay_transaction_list);
        mPayTransaction = new ArrayList<PayTransactionModel>();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mLinearLayoutManager);
        progressDialog = new ProgressDialog(PayTransaction.this);
        progressDialog.setMessage("Data loading");
        progressDialog.show();
    }

    public void onBackPressed() {
        if(user.getEmail().equalsIgnoreCase("shahabafridi11@gmail.com")){
            Intent intent = new Intent(PayTransaction.this, AdminHome.class);
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
            Intent intent = new Intent(PayTransaction.this, Home.class);
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