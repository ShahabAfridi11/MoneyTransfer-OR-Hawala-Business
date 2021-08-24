package com.example.moneytransfer.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytransfer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TransactionProfile extends AppCompatActivity {

    private TextView mSenderNameTvT, mReceiverNameTvT, mSPhoneNumberTvT,
            mRPhoneNumberTvT, mSenderCnicTvT, mReceiverCnicTvT, mTotalAmountT,
            mSendingAdminTv, mPayingAdminTv;

    Bundle mGetValue;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_profile);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        init();
        getAndSetIntentValues();
    }

    private void init() {
        mSenderNameTvT = findViewById(R.id.sender_name_tv_t);
        mReceiverNameTvT = findViewById(R.id.receiver_name_tv_t);
        mSenderCnicTvT = findViewById(R.id.sender_cnic_tv_t);
        mReceiverCnicTvT = findViewById(R.id.receiver_cnic_tv_t);
        mSPhoneNumberTvT = findViewById(R.id.sender_phone_number_tv_t);
        mRPhoneNumberTvT = findViewById(R.id.receiver_phone_number_tv_t);
        mTotalAmountT = findViewById(R.id.amount_tv_t);
        mSendingAdminTv = findViewById(R.id.sending_admin_tv_t);
        mPayingAdminTv = findViewById(R.id.receiving_admin_tv_t);

    }

    private void getAndSetIntentValues() {
        mGetValue = getIntent().getExtras();



        mSenderNameTvT.setText("Name:  "+mGetValue.getString("sendername"));
        mSenderCnicTvT.setText("Cnic:  "+mGetValue.getString("sendercnic"));
        mSPhoneNumberTvT.setText("Phone Number:  "+mGetValue.getString("senderpnumber"));
        mReceiverNameTvT.setText("Name:  "+mGetValue.getString("receivername"));
        mReceiverCnicTvT.setText("Cnic:  "+mGetValue.getString("receivercnic"));
        mRPhoneNumberTvT.setText("Phone Number:  "+mGetValue.getString("receiverpnumber"));
        mTotalAmountT.setText("Amount:  "+mGetValue.getString("total"));
        mSendingAdminTv.setText("Sending Adnin:  "+mGetValue.getString("sendingAdmin"));
        mPayingAdminTv.setText("Paying Admin:  "+mGetValue.getString("payingAdmin"));

    }

    public void onBackPressed() {

            finish();
    }
}