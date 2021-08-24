package com.example.moneytransfer.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.activities.PayTransactionProfile;
import com.example.moneytransfer.activities.activities.TransactionProfile;
import com.example.moneytransfer.activities.models.AdminModel;
import com.example.moneytransfer.activities.models.PayTransactionModel;
import com.example.moneytransfer.activities.models.TransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    ArrayList<TransactionModel> transactionModel = new ArrayList<>();
    Context context;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public TransactionAdapter(ArrayList<TransactionModel> transactionModel, Context context) {
        this.transactionModel = transactionModel;
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
        View view = layoutInflater.inflate(R.layout.transaction_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TransactionAdapter.ViewHolder holder, int position) {
        final TransactionModel item = transactionModel.get(position);
        holder.mSenderName.setText("Sender Name: "+item.getSendername());
        holder.mmReceiverName.setText("Receiver Name: "+item.getReceivername());
        holder.mAmount.setText("Amount: "+item.getTotalamount());


        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TransactionProfile.class);

                FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        if(snapshot.exists()){
                            AdminModel adminModel = snapshot.getValue(AdminModel.class);
                            if(adminModel.getUseremail().equalsIgnoreCase("Shahabafridi11@gmail.com")){

                                intent.putExtra("sendername",item.getSendername());
                                intent.putExtra("sendercnic",item.getSendercnic());
                                intent.putExtra("senderpnumber", item.getSenderphonenumber());
                                intent.putExtra("receivercnic",item.getReceivercnic());
                                intent.putExtra("receivername", item.getReceivername());
                                intent.putExtra("receiverpnumber", item.getReceiverphonenumber());
                                intent.putExtra("payingbranch", item.getPayingbranch());
                                intent.putExtra("sendingAdmin", item.getSendingadmin());
                                intent.putExtra("payingAdmin", item.getPayingadmin());
                                intent.putExtra("total", item.getTotalamount());
                                intent.putExtra("sendingdate", item.getSendingdate());
                                intent.putExtra("payingdate", item.getPayingdate());

                                context.startActivity(intent);


                            }
//                            else if(adminModel.getUseremail().equalsIgnoreCase(user.getEmail())){
//                                intent.putExtra("name", adminModel.getFirstname() + " " + adminModel.getSecondname());
//                                intent.putExtra("useremail", adminModel.getUseremail());
//                                intent.putExtra("fathername", adminModel.getFathername());
//                                intent.putExtra("cnicnumber", adminModel.getCnicnumber());
//                                intent.putExtra("cnicpath", adminModel.getCnicpath());
//                                intent.putExtra("profilepic", adminModel.getProfilepath());
//                                intent.putExtra("phonenumber", adminModel.getPhonenumber());
//                                intent.putExtra("date", adminModel.getDate());
//                                intent.putExtra("password", adminModel.getPassword());
//                                intent.putExtra("branch",adminModel.getBranchname());
//
//                                intent.putExtra("sendername",item.getSendername());
//                                intent.putExtra("sendercnic",item.getSendercnic());
//                                intent.putExtra("senderpnumber", item.getSenderphonenumber());
//                                intent.putExtra("receivercnic",item.getReceivercnic());
//                                intent.putExtra("receivername", item.getReceivername());
//                                intent.putExtra("receiverpnumber", item.getReceiverphonenumber());
//                                intent.putExtra("payingbranch", item.getPayingbranch());
//                                intent.putExtra("sendingAdmin", item.getSendingadmin());
//                                intent.putExtra("payingAdmin", item.getPayingadmin());
//                                intent.putExtra("total", item.getTotalamount());
//                                intent.putExtra("sendingdate", item.getSendingdate());
//                                intent.putExtra("payingdate", item.getPayingdate());
//
//                                context.startActivity(intent);
//                                ((Activity)context).finish();
//                            }
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
        return transactionModel.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mSenderName, mmReceiverName, mAmount;
        LinearLayout mDetail;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mSenderName = itemView.findViewById(R.id.p_transaction_sname_t);
            mmReceiverName = itemView.findViewById(R.id.transaction_rname_t);
            mAmount = itemView.findViewById(R.id.transaction_amount_t);
            mDetail = itemView.findViewById(R.id.detail_t);





        }
    }
}
