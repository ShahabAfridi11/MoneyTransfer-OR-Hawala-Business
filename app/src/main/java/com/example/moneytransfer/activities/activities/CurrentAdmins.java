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
import com.example.moneytransfer.activities.adapters.CurrentAdminAdapter;
import com.example.moneytransfer.activities.models.AdminModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CurrentAdmins extends AppCompatActivity {

    private RecyclerView mAdminrecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CurrentAdminAdapter mAdminControlAdapter;
    private ArrayList<AdminModel> mAdmin;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private Bundle mGetValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_admins);

        mGetValue = getIntent().getExtras();

        init();
        loadFireBaseDate();
        if(mAdmin.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(CurrentAdmins.this, "No Admin Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFireBaseDate() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();

                    AdminModel adminModel = dataSnapshot.getValue(AdminModel.class);
                    mAdmin.add(adminModel);


                    mAdminControlAdapter = new CurrentAdminAdapter(mAdmin, CurrentAdmins.this);
                    mAdminControlAdapter.notifyDataSetChanged();

                    mAdminrecyclerView.setAdapter(mAdminControlAdapter);

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
        mAdminrecyclerView = findViewById(R.id.admin_list_view);
        mAdmin = new ArrayList<AdminModel>();
        mLinearLayoutManager= new LinearLayoutManager(this);
        mAdminrecyclerView.setLayoutManager(mLinearLayoutManager);
        progressDialog = new ProgressDialog(CurrentAdmins.this);
        progressDialog.setMessage("Data loading");
        progressDialog.show();


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CurrentAdmins.this, AdminHome.class);
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