package com.example.moneytransfer.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.activities.AdminDetail;
import com.example.moneytransfer.activities.activities.AdminsControl;
import com.example.moneytransfer.activities.activities.LoginScreen;
import com.example.moneytransfer.activities.activities.SignUpScreen;
import com.example.moneytransfer.activities.models.AdminModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminControlAdapter extends RecyclerView.Adapter<AdminControlAdapter.ViewHolder> {

    ArrayList<AdminModel> adminModel = new ArrayList<>();
    Context context;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    public AdminControlAdapter(ArrayList<AdminModel> adminModel, Context context) {
        this.adminModel = adminModel;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.admin_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminControlAdapter.ViewHolder holder, int position) {
        final AdminModel adminItem = adminModel.get(position);
        holder.mCnicNumber.setText(adminItem.getCnicnumber());
        holder.mUserName.setText(adminItem.getFirstname()+" "+adminItem.getSecondname());
        holder.mPhoneNumber.setText(adminItem.getPhonenumber());
        Glide.with(context).load(adminItem.getProfilepath()).into(holder.mprofileIMG);

        holder.mAdminDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, AdminDetail.class);

                intent.putExtra("adminusername",adminItem.getFirstname()+" "+adminItem.getSecondname());
                intent.putExtra("adminuseremail",adminItem.getUseremail());
                intent.putExtra("adminfathername",adminItem.getFathername());
                intent.putExtra("admincnicnumber",adminItem.getCnicnumber());
                intent.putExtra("admincnicpath",adminItem.getCnicpath());
                intent.putExtra("adminprofilepic",adminItem.getProfilepath());
                intent.putExtra("adminphonenumber",adminItem.getPhonenumber());
                intent.putExtra("admindate",adminItem.getDate());
                intent.putExtra("adminpassword",adminItem.getPassword());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminDetail.class);
                intent.putExtra("username",adminItem.getFirstname()+" "+adminItem.getSecondname());
                intent.putExtra("useremail",adminItem.getUseremail());
                intent.putExtra("fathername",adminItem.getFathername());
                intent.putExtra("cnicnumber",adminItem.getCnicnumber());
                intent.putExtra("cnicpath",adminItem.getCnicpath());
                intent.putExtra("profilepic",adminItem.getProfilepath());
                intent.putExtra("phonenumber",adminItem.getPhonenumber());
                intent.putExtra("date",adminItem.getDate());
                intent.putExtra("password",adminItem.getPassword());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
        holder.mAdminDeclineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseStorage.getInstance().getReference().child("images").child(adminItem.getProfilepath()).delete();
                FirebaseStorage.getInstance().getReference().child("images").child(adminItem.getCnicpath()).delete();
                FirebaseDatabase.getInstance().getReference().child("requestedusers").
                        child(adminItem.getFirstname()).setValue(null);
                holder.mAdminDeclineBtn.setVisibility(View.GONE);
                holder.mAdminAllowBtn.setVisibility(View.GONE);
            }
        });

        holder.mAdminAllowBtn.setOnClickListener(new View.OnClickListener() {

            HashMap<String,String> myData;
            String currentUser;

            @Override
            public void onClick(View v) {
                firebaseAuth.createUserWithEmailAndPassword(adminItem.getUseremail(),adminItem.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            holder.mAdminAllowBtn.setVisibility(View.GONE);
                            holder.mAdminDeclineBtn.setVisibility(View.GONE);

                        }
                    }
                });

                myData = new HashMap<String,String>();
                myData.put("firstname", adminItem.getFirstname());
                myData.put("secondname", adminItem.getSecondname());
                myData.put("fathername", adminItem.getFathername());
                myData.put("cnicnumber", adminItem.getCnicnumber());
                myData.put("phonenumber", adminItem.getPhonenumber());
                myData.put("useremail", adminItem.getUseremail());
                myData.put("password", adminItem.getPassword());
                myData.put("date", adminItem.getDate());
                myData.put("cnicpath", adminItem.getCnicpath());
                myData.put("profilepath", adminItem.getProfilepath());
                myData.put("branchname", adminItem.getBranchname());

                databaseReference.child("users").push().setValue(myData).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context, "Admin Added Sucessfully", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

                holder.mAdminAllowBtn.setVisibility(View.INVISIBLE);
                holder.mAdminDeclineBtn.setVisibility(View.INVISIBLE);
                FirebaseDatabase.getInstance().getReference().child("requestedusers").
                        child(adminItem.getFirstname()).setValue(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminModel.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mprofileIMG;
        LinearLayout mAdminDetail;
        TextView mCnicNumber, mUserName, mPhoneNumber;
        Button mAdminDeclineBtn, mAdminAllowBtn;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mprofileIMG = itemView.findViewById(R.id.admin_profil_pic);
            mCnicNumber = itemView.findViewById(R.id.adimn_cnic);
            mUserName = itemView.findViewById(R.id.adimn_name);
            mPhoneNumber = itemView.findViewById(R.id.adimn_phoneNumber);
            mAdminDeclineBtn = itemView.findViewById(R.id.admin_decline);
            mAdminAllowBtn = itemView.findViewById(R.id.admin_allow);
            mAdminDetail = itemView.findViewById(R.id.admin_detail);
            mAdminDetail = itemView.findViewById(R.id.admin_detail);




        }
    }
}
