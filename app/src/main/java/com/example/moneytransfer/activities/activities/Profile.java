package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.models.AdminModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private CircleImageView imageView;
    private TextView mFirstNameTv, mSecondNameTv, mFatherNameTv, mEmailTv,
            mPasswordTv, mCnicNumberTv, mPhoneNumberTv,mBranch;
    private ImageView mCnicPicture;

    private Bundle mGetValue;
    private String cnicpath,profileimagePath,childName;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        getAndSetIntentValues();
    }

    private void init() {
        imageView = findViewById(R.id.profile_pic_detail);
        mFirstNameTv = findViewById(R.id.profile_firstName_tv);
        mFatherNameTv = findViewById(R.id.profile_fatherName_tv);
        mPhoneNumberTv = findViewById(R.id.profile_phoneNumber_tv);
        mEmailTv = findViewById(R.id.profile_email_tv);
        mPasswordTv = findViewById(R.id.profile_password_tv);
        mCnicPicture = findViewById(R.id.profile_Cnic_img_view);
        mBranch = findViewById(R.id.profile_branch_tv);
        mCnicNumberTv = findViewById(R.id.profile_cnic_tv);
    }

    private void getAndSetIntentValues() {
        mGetValue = getIntent().getExtras();

        profileimagePath = mGetValue.getString("profilepic");
        cnicpath = mGetValue.getString("cnicpath");

        Toast.makeText(this, mGetValue.getString("cnicnumber"), Toast.LENGTH_SHORT).show();

        mFirstNameTv.setText("Name:   "+mGetValue.getString("name"));
        mFatherNameTv.setText("Father Name:   "+mGetValue.getString("fathername"));
        mCnicNumberTv.setText("CNIC Number:   "+mGetValue.getString("cnicnumber"));
        mPhoneNumberTv.setText("Phone Number:   "+mGetValue.getString("phonenumber"));
        mEmailTv.setText("Email:   "+mGetValue.getString("useremail"));
        mPasswordTv.setText("Password:   "+mGetValue.getString("password"));
        mBranch.setText("Branch: "+mGetValue.getString("branch"));

        Glide.with(Profile.this).load(profileimagePath).into(imageView);
        Glide.with(Profile.this).load(cnicpath).into(mCnicPicture);
    }

    public void onBackPressed() {

        if(mGetValue.getString("useremail").equalsIgnoreCase("Shahabafridi11@gmail.com")){
            Intent intent = new Intent(Profile.this, AdminHome.class);
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
        }else {
            Intent intent = new Intent(Profile.this, Home.class);
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