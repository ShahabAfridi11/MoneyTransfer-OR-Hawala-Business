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
import com.example.moneytransfer.activities.adapters.PayTransactionAdapter;
import com.example.moneytransfer.activities.adapters.TransactionAdapter;
import com.example.moneytransfer.activities.models.PayTransactionModel;
import com.example.moneytransfer.activities.models.TransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Transactions extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TransactionAdapter mTransactionAdapter;
    private ArrayList<TransactionModel> mTransaction;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private Bundle mGetValue;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        init();
        loadFireBaseDate();
        if(mTransaction.isEmpty()){
            progressDialog.dismiss();
            Toast.makeText(Transactions.this, "No Record Pound", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFireBaseDate() {
        mGetValue = getIntent().getExtras();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("payedtransaction");

        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();

                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);

                    if (mGetValue.getString("useremail").equalsIgnoreCase(user.getEmail())){
                        if(transactionModel.getPayingbranch().equalsIgnoreCase(mGetValue.getString("branch"))){
                            mTransaction.add(transactionModel);


                            mTransactionAdapter = new TransactionAdapter(mTransaction, Transactions.this);
                            mTransactionAdapter.notifyDataSetChanged();

                            mrecyclerView.setAdapter(mTransactionAdapter);

                        }else {
                            mTransaction.add(transactionModel);


                            mTransactionAdapter = new TransactionAdapter(mTransaction, Transactions.this);
                            mTransactionAdapter.notifyDataSetChanged();

                            mrecyclerView.setAdapter(mTransactionAdapter);
                        }
                    } else {

                    }


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Transactions.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
        mrecyclerView = findViewById(R.id.transaction_list);
        mTransaction = new ArrayList<TransactionModel>();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mLinearLayoutManager);
        progressDialog = new ProgressDialog(Transactions.this);
        progressDialog.setMessage("Data loading");
        progressDialog.show();
    }

    public void onBackPressed() {

        if(user.getEmail().equalsIgnoreCase("shahabafridi11@gmail.com")){
            Intent intent = new Intent(Transactions.this, AdminHome.class);
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
            Intent intent = new Intent(Transactions.this, Home.class);
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