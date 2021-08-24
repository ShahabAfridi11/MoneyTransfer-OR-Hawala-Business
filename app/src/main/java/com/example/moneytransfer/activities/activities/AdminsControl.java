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
import com.example.moneytransfer.activities.models.AdminModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class AdminsControl extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private AdminControlAdapter mAdminControlAdapter;
    private ArrayList<AdminModel> mAdmin;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private Bundle mGetValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_control);

        mGetValue = getIntent().getExtras();

        init();
        loadFireBaseDate();
        if(mAdmin.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(AdminsControl.this, "No Request Found", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadFireBaseDate() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("requestedusers");

        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();

                    AdminModel adminModel = dataSnapshot.getValue(AdminModel.class);
                    mAdmin.add(adminModel);


                    mAdminControlAdapter = new AdminControlAdapter(mAdmin, AdminsControl.this);
                    mAdminControlAdapter.notifyDataSetChanged();

                    mrecyclerView.setAdapter(mAdminControlAdapter);

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
        mrecyclerView = findViewById(R.id.admin_list);
        mAdmin = new ArrayList<AdminModel>();
        mLinearLayoutManager= new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mLinearLayoutManager);
        progressDialog = new ProgressDialog(AdminsControl.this);
        progressDialog.setMessage("Data loading");
        progressDialog.show();


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminsControl.this,AdminHome.class);
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