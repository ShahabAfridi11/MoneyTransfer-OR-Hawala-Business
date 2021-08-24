package com.example.moneytransfer.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.activities.AdminDetail;
import com.example.moneytransfer.activities.activities.PayTransactionProfile;
import com.example.moneytransfer.activities.models.AdminModel;
import com.example.moneytransfer.activities.models.PayTransactionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class PayTransactionAdapter extends RecyclerView.Adapter<PayTransactionAdapter.ViewHolder> {

    ArrayList<PayTransactionModel> payTransactionModel = new ArrayList<>();
    Context context;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public PayTransactionAdapter(ArrayList<PayTransactionModel> payTransactionModel, Context context) {
        this.payTransactionModel = payTransactionModel;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pay_transaction_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PayTransactionAdapter.ViewHolder holder, int position) {
        final PayTransactionModel item = payTransactionModel.get(position);
        holder.mSenderName.setText("Sender Name: "+item.getSendername());
        holder.mmReceiverName.setText("Receiver Name: "+item.getReceivername());
        holder.mAmount.setText("Amount: "+item.getTotalamount());


        holder.mTransactionDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PayTransactionProfile.class);

                FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        if(snapshot.exists()){
                            AdminModel adminModel = snapshot.getValue(AdminModel.class);
                            if(adminModel.getUseremail().equalsIgnoreCase("Shahabafridi11@gmail.com")){
                                intent.putExtra("name",adminModel.getFirstname()+" "+adminModel.getSecondname());
                                intent.putExtra("useremail",adminModel.getUseremail());
                                intent.putExtra("fathername",adminModel.getFathername());
                                intent.putExtra("cnicnumber",adminModel.getCnicnumber());
                                intent.putExtra("cnicpath",adminModel.getCnicpath());
                                intent.putExtra("profilepic",adminModel.getProfilepath());
                                intent.putExtra("phonenumber",adminModel.getPhonenumber());
                                intent.putExtra("date",adminModel.getDate());
                                intent.putExtra("password",adminModel.getPassword());
                                intent.putExtra("branch",adminModel.getBranchname());

                                intent.putExtra("sendername",item.getSendername());
                                intent.putExtra("sendercnic",item.getSendercnic());
                                intent.putExtra("senderpnumber", item.getSenderphonenumber());
                                intent.putExtra("receivercnic",item.getReceivercnic());
                                intent.putExtra("receivername", item.getReceivername());
                                intent.putExtra("receiverpnumber", item.getReceiverphonenumber());
                                intent.putExtra("receivingbranch", item.getReceivingbranch());
                                intent.putExtra("sendingAdmin", item.getSendingadmin());
                                intent.putExtra("total", item.getTotalamount());
                                intent.putExtra("sendingdate", item.getSendingdate());

                                context.startActivity(intent);
                                ((Activity)context).finish();


                            }else if(adminModel.getUseremail().equalsIgnoreCase(user.getEmail())){
                                intent.putExtra("name", adminModel.getFirstname() + " " + adminModel.getSecondname());
                                intent.putExtra("useremail", adminModel.getUseremail());
                                intent.putExtra("fathername", adminModel.getFathername());
                                intent.putExtra("cnicnumber", adminModel.getCnicnumber());
                                intent.putExtra("cnicpath", adminModel.getCnicpath());
                                intent.putExtra("profilepic", adminModel.getProfilepath());
                                intent.putExtra("phonenumber", adminModel.getPhonenumber());
                                intent.putExtra("date", adminModel.getDate());
                                intent.putExtra("password", adminModel.getPassword());
                                intent.putExtra("branch",adminModel.getBranchname());

                                intent.putExtra("sendername",item.getSendername());
                                intent.putExtra("sendercnic",item.getSendercnic());
                                intent.putExtra("senderpnumber", item.getSenderphonenumber());
                                intent.putExtra("receivercnic",item.getReceivercnic());
                                intent.putExtra("receivername", item.getReceivername());
                                intent.putExtra("receiverpnumber", item.getReceiverphonenumber());
                                intent.putExtra("receivingbranch", item.getReceivingbranch());
                                intent.putExtra("sendingAdmin", item.getSendingadmin());
                                intent.putExtra("total", item.getTotalamount());
                                intent.putExtra("sendingdate", item.getSendingdate());

                                context.startActivity(intent);
                                ((Activity)context).finish();
                            }
                            else if(snapshot.getChildrenCount() <= 0){
                                Toast.makeText(context, "No Payable Transaction Found", Toast.LENGTH_SHORT).show();
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
        });

    }

    @Override
    public int getItemCount() {
        return payTransactionModel.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mSenderName, mmReceiverName, mAmount;
        Button mTransactionDetailBtn;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mSenderName = itemView.findViewById(R.id.p_transaction_sname);
            mmReceiverName = itemView.findViewById(R.id.p_transaction_rname);
            mAmount = itemView.findViewById(R.id.p_transaction_amount);
            mTransactionDetailBtn = itemView.findViewById(R.id.p_transaction_view);





        }
    }
}
