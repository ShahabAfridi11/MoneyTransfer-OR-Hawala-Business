package com.example.moneytransfer.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.activities.AdminDetail;
import com.example.moneytransfer.activities.models.AdminModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CurrentAdminAdapter extends RecyclerView.Adapter<CurrentAdminAdapter.ViewHolderAdmin> {

    ArrayList<AdminModel> admin = new ArrayList<>();
    Context context;

    public CurrentAdminAdapter(ArrayList<AdminModel> admin, Context context) {
        this.admin = admin;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderAdmin onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.admin_view,parent, false);
        ViewHolderAdmin viewHolderAdmin = new ViewHolderAdmin(view);
         return viewHolderAdmin;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CurrentAdminAdapter.ViewHolderAdmin holder, int position) {
        AdminModel adminItem = admin.get(position);
        Glide.with(context).load(adminItem.getProfilepath()).into(holder.imageView);
        holder.mCnic.setText(adminItem.getCnicnumber());
        holder.muserName.setText(adminItem.getFirstname()+" "+adminItem.getSecondname());
        holder.mPhoneNumber.setText(adminItem.getPhonenumber());

        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminDetail.class);
                intent.putExtra("adminname",adminItem.getFirstname()+" "+adminItem.getSecondname());
                intent.putExtra("adminemail",adminItem.getUseremail());
                intent.putExtra("adminfathername",adminItem.getFathername());
                intent.putExtra("admincnicnumber",adminItem.getCnicnumber());
                intent.putExtra("admincnicpath",adminItem.getCnicpath());
                intent.putExtra("adminprofilepic",adminItem.getProfilepath());
                intent.putExtra("adminphonenumber",adminItem.getPhonenumber());
                intent.putExtra("admindate",adminItem.getDate());
                intent.putExtra("adminpassword",adminItem.getPassword());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return admin.size();
    }

    public static class ViewHolderAdmin extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView mCnic, muserName, mPhoneNumber;
        Button mDetail;

        public ViewHolderAdmin(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ad_profil_pic_view);
            mCnic = itemView.findViewById(R.id.ad_cnic_view);
            muserName = itemView.findViewById(R.id.ad_name_view);
            mPhoneNumber = itemView.findViewById(R.id.ad_phoneNumber_view);
            mDetail = itemView.findViewById(R.id.admin_d_view);
        }
    }
}
