package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.models.PayTransactionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class PayTransactionProfile extends AppCompatActivity {

    private TextView mSenderNameTv,mReceiverNameTv, mSPhoneNumberTv,
            mRPhoneNumberTv, mSenderCnicTv, mReceiverCnicTv, mTotalAmount;
    private Button mPayTransaction;
    Bundle mGetValue;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_transaction_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        init();
        getAndSetIntentValues();

        payTransaction();
    }

    private void payTransaction() {
        String payingDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        mPayTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> myData = new HashMap<String, String>();
                myData.put("sendername", mGetValue.getString("sendername"));
                myData.put("receivername", mGetValue.getString("receivername"));
                myData.put("sendercnic", mGetValue.getString("sendercnic"));
                myData.put("receivercnic", mGetValue.getString("receivercnic"));
                myData.put("senderphonenumber", mGetValue.getString("senderpnumber"));
                myData.put("receiverphonenumber", mGetValue.getString("receiverpnumber"));
                myData.put("payingbranch", mGetValue.getString("branch"));
                myData.put("payingadmin", mGetValue.getString("useremail"));
                myData.put("totalamount", mGetValue.getString("total"));
                myData.put("sendingadmin", mGetValue.getString("sendingAdmin"));
                myData.put("payingdate", payingDate);
                myData.put("sendingdate", mGetValue.getString("sendingdate"));

                databaseReference.child("payedtransaction").push().setValue(myData).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(PayTransactionProfile.this, "Payed Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                FirebaseDatabase.getInstance().getReference().child("newtransaction").
                        addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        if(snapshot.exists()){
                            PayTransactionModel item = snapshot.getValue(PayTransactionModel.class);

                            if(item.getSendername().equals(mGetValue.getString("sendername"))){
                                if(item.getReceivername().equals(mGetValue.getString("receivername"))){
                                    if(item.getTotalamount().equals(mGetValue.getString("total"))){
                                        String s = snapshot.getKey().toString();

                                        FirebaseDatabase.getInstance().getReference().child("newtransaction").
                                                child(s).setValue(null);
                                    }
                                }
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

                Intent intent = new Intent(PayTransactionProfile.this, PayTransaction.class);
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
        });

    }

    private void getAndSetIntentValues() {
        mGetValue = getIntent().getExtras();



        mSenderNameTv.setText("Name:  "+mGetValue.getString("sendername"));
        mSenderCnicTv.setText("Cnic:  "+mGetValue.getString("sendercnic"));
        mSPhoneNumberTv.setText("Phone Number:  "+mGetValue.getString("senderpnumber"));
        mReceiverNameTv.setText("Name:  "+mGetValue.getString("receivername"));
        mReceiverCnicTv.setText("Cnic:  "+mGetValue.getString("receivercnic"));
        mRPhoneNumberTv.setText("Phone Number:  "+mGetValue.getString("receiverpnumber"));
        mTotalAmount.setText("Amount:  "+mGetValue.getString("total"));

    }

    private void init() {
        mSenderNameTv = findViewById(R.id.sender_name_tv);
        mReceiverNameTv = findViewById(R.id.receiver_name_tv);
        mSenderCnicTv = findViewById(R.id.sender_cnic_tv);
        mReceiverCnicTv = findViewById(R.id.receiver_cnic_tv);
        mSPhoneNumberTv = findViewById(R.id.sender_phone_number_tv);
        mRPhoneNumberTv = findViewById(R.id.receiver_phone_number_tv);
        mTotalAmount = findViewById(R.id.amount_tv);
        mPayTransaction = findViewById(R.id.pay_transaction);

    }

    public void onBackPressed() {
            Intent intent = new Intent(PayTransactionProfile.this, PayTransaction.class);
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