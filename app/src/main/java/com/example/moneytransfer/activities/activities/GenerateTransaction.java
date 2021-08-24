
package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytransfer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class GenerateTransaction extends AppCompatActivity {

    private EditText mSenderNameEt,mReceiverNameEt, mSPhoneNumberEt, mRPhoneNumberEt, mAmount;
    private MaskedEditText mSenderCnicEt, mReceiverCnicEt;
    private TextView mTotalAmount;
    private Button mEnterNewTransaction,mRefresAmount;
    Bundle mGetValue;

    private Spinner mRates;
    ArrayList<String> list;
    ValueEventListener valueEventListener;
    ArrayAdapter<String> RatesAdapter;

    private Spinner mRecivingBranch;
    ArrayList<String> branchesList;
    ValueEventListener branchesValueEventListener;
    ArrayAdapter<String> BranchesAdapter;

    boolean netConnection;

    private String senderName, receiverName, senderCnic,
            receiverCnic, senderPhoneNumber, receiverPhoneNumber, receivingBranch, sendingBranch,
            totalAmount, sendingAdmin, sendingDate;

    private double rate;
    double formula;
    double total;
    double sendAmount;

    private DatabaseReference databaseReferenceSpinner;
    private DatabaseReference databaseReferenceBranchesSpinner;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceBranches;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_transaction);

        init();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReferenceSpinner = FirebaseDatabase.getInstance().getReference("exchangerates");
        databaseReferenceBranchesSpinner = FirebaseDatabase.getInstance().getReference("branches");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        addExchangeRatesToSpinner();
        addBranchesToSpinner();
        addTransaction();
        checkConnection();

        mRefresAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAmount.getText().equals("")){
                    Toast.makeText(GenerateTransaction.this, "Must Enter Amount", Toast.LENGTH_SHORT).show();
                    mAmount.setError("Must Enter Amount");
                } else {
         //           total = Double.parseDouble(mTotalAmount.getText().toString());
                    sendAmount = Double.parseDouble(mAmount.getText().toString());

                    databaseReferenceSpinner = FirebaseDatabase.getInstance().getReference().child("exchangerates");
                    databaseReferenceSpinner.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                            if(snapshot.exists()){
                                //      Toast.makeText(GenerateTransaction.this, snapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
                                if(mRates.getSelectedItem().toString().equals(snapshot.getKey().toString())) {
                                    rate = Double.parseDouble(snapshot.getValue().toString());
                                    formula = sendAmount * rate;
                                    long i = (new Double(formula)).longValue();
                                    mTotalAmount.setText(String.valueOf(i));
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
            }
        });
    }

    private void init() {
        mGetValue = getIntent().getExtras();
        mRates = findViewById(R.id.currency_type_spinner);
        mRecivingBranch = findViewById(R.id.reciving_branch);
        mEnterNewTransaction = findViewById(R.id.generate_transaction);
        mTotalAmount = findViewById(R.id.total_amount);
        mSenderNameEt = findViewById(R.id.sender_name_et);
        mReceiverNameEt = findViewById(R.id.receiver_name_et);
        mSenderCnicEt = findViewById(R.id.sender_cnic_et);
        mReceiverCnicEt = findViewById(R.id.receiver_cnic_et);
        mSPhoneNumberEt = findViewById(R.id.sender_phone_number_et);
        mRPhoneNumberEt = findViewById(R.id.receiver_phone_number_et);
        mAmount = findViewById(R.id.amount);
        mRefresAmount = findViewById(R.id.refresh_amount);
    }

    public void addTransaction() {

        senderName = mSenderNameEt.getText().toString();
        receiverName = mReceiverNameEt.getText().toString().trim();
        senderCnic = mSenderCnicEt.getText().toString().trim();
        receiverCnic = mReceiverCnicEt.getText().toString().trim();
        senderPhoneNumber = mSPhoneNumberEt.getText().toString().trim();
        receiverPhoneNumber = mRPhoneNumberEt.getText().toString().trim();
        sendingBranch = mGetValue.getString("adminbranch");
        receivingBranch = mRecivingBranch.getSelectedItem().toString();
        sendingAdmin = mGetValue.getString("useremail");
        totalAmount = mTotalAmount.getText().toString().trim();
        sendingDate =new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        mEnterNewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(netConnection) {
                    if (mSenderNameEt.getText().equals("")) {
                        mSenderNameEt.setError("Sender Name required!");
                    } else if (mSenderCnicEt.getText().equals("")) {
                        mSenderCnicEt.setError("Cnic Number required!");
                    }else if (mSPhoneNumberEt.getText().equals("")) {
                        mSPhoneNumberEt.setError("Phone Number required!");
                    }else if (mReceiverNameEt.getText().equals("")) {
                        mReceiverNameEt.setError("Receiver Name required!");
                    } else if (mReceiverCnicEt.getText().equals("")) {
                        mReceiverCnicEt.setError("Cnic Number Required!");
                    } else if (mRPhoneNumberEt.getText().equals("")) {
                        mRPhoneNumberEt.setError("Phone Number Required!");
                    }else if (mRecivingBranch.getSelectedItem().equals("Select Receiving Branch")) {
                        ((TextView)mRecivingBranch.getSelectedView()).setError("Select Branch Please");
                    } else if (Integer.valueOf(mAmount.getText().toString()) <= 0) {
                        mAmount.setError("Enter Valid Amount");
                    }else if (mTotalAmount.getText().equals("")) {
                        mTotalAmount.setError("Need Amount");
                    } else {
                        Toast.makeText(GenerateTransaction.this, mGetValue.getString("adminbranch"), Toast.LENGTH_SHORT).show();

                        HashMap<String, String> myData = new HashMap<String, String>();
                        myData.put("sendername", mSenderNameEt.getText().toString());
                        myData.put("receivername", mReceiverNameEt.getText().toString());
                        myData.put("sendercnic", mSenderCnicEt.getText().toString());
                        myData.put("receivercnic", mReceiverCnicEt.getText().toString());
                        myData.put("senderphonenumber", mSPhoneNumberEt.getText().toString());
                        myData.put("receiverphonenumber", mRPhoneNumberEt.getText().toString());
                        myData.put("receivingbranch", mRecivingBranch.getSelectedItem().toString());
                        myData.put("sendingbranch", sendingBranch);
                        myData.put("totalamount", mTotalAmount.getText().toString());
                        myData.put("sendingadmin", sendingAdmin);
                        myData.put("sendingdate", sendingDate);


                        databaseReference.child("newtransaction").push().setValue(myData).
                                addOnCompleteListener(GenerateTransaction.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            if(user.getEmail().equalsIgnoreCase("shahabafridi11@gmail.com")){
                                                Toast.makeText(GenerateTransaction.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(GenerateTransaction.this, AdminHome.class);
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
                                                Toast.makeText(GenerateTransaction.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(GenerateTransaction.this, Home.class);
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
                                });
                    }
                }
            }
        });

    }

    public void addExchangeRatesToSpinner() {
        //Add Currency Types To Spinner
        list = new ArrayList<>();
        list.clear();
        list.add("Select Currenct Type");
        RatesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        mRates.setAdapter(RatesAdapter);
        RatesAdapter.notifyDataSetChanged();

        valueEventListener = databaseReferenceSpinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot myData : snapshot.getChildren()){

                    if (myData.getKey().toString().equals("udateddate")){

                    }else {
                        list.add(myData.getKey().toString());
                        RatesAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void addBranchesToSpinner() {

        //Add Branches To Spinner
        branchesList = new ArrayList<>();
        branchesList.clear();
        branchesList.add("Select Receiving Branch");
//        mRecivingBranch.setPrompt("Select Receiving Branch");
        Collections.sort(branchesList);
        BranchesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, branchesList);
        mRecivingBranch.setAdapter(BranchesAdapter);
        BranchesAdapter.notifyDataSetChanged();

        branchesValueEventListener = databaseReferenceBranchesSpinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot myData : snapshot.getChildren()){
                        branchesList.add(myData.getValue().toString());
                        BranchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void checkConnection(){
        netConnection=false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            netConnection = true;
        }
        else{
            netConnection = false;
        }
    }

    @Override
    public void onBackPressed() {
        if(user.getEmail().equalsIgnoreCase("shahabafridi11@gmail.com")){
            Intent intent = new Intent(GenerateTransaction.this, AdminHome.class);
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
            Intent intent = new Intent(GenerateTransaction.this, Home.class);
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