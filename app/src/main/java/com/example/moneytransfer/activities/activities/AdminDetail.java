package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.models.AdminModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDetail extends AppCompatActivity {

    private CircleImageView imageView;
    private TextView mFirstNameTv, mSecondNameTv, mFatherNameTv, mEmailTv,
            mPasswordTv, mCnicNumberTv, mPhoneNumberTv;
    private ImageView mCnicPicture;
    private Bundle mGetValue;
    private String cnicpath,profileimagePath,childName;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseUser userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail);

        init();
        getAndSetIntentValues();
        getAndSetDataInFirebase();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    private void getAndSetIntentValues() {


        profileimagePath = mGetValue.getString("adminprofilepic");
        cnicpath = mGetValue.getString("admincnicpath");

        mFirstNameTv.setText("Name: "+mGetValue.getString("adminname"));
        mFatherNameTv.setText("Father Name: "+mGetValue.getString("adminfathername"));
        mCnicNumberTv.setText("CNIC: "+mGetValue.getString("admincnicnumber"));
        mPhoneNumberTv.setText("Phone Number: "+mGetValue.getString("adminphonenumber"));
        mEmailTv.setText("Email: "+mGetValue.getString("adminemail"));
        mPasswordTv.setText("Password: "+mGetValue.getString("adminpassword"));


        //todo for loading image from path

        Glide.with(AdminDetail.this).load(profileimagePath).into(imageView);
        Glide.with(AdminDetail.this).load(cnicpath).into(mCnicPicture);
    }

    private void getAndSetDataInFirebase() {
        mGetValue = getIntent().getExtras();
        childName = mGetValue.getString("username");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(user.getEmail().equalsIgnoreCase("Shahabafridi11@gmail.com")){
            inflater.inflate(R.menu.delete_admin_menu, menu);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                FirebaseStorage.getInstance().getReference().child("images").child(mGetValue.getString("profilepic")).delete();
                FirebaseStorage.getInstance().getReference().child("images").child(mGetValue.getString("cnicpath")).delete();
                FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        if(snapshot.exists()){
                            AdminModel adminModel = snapshot.getValue(AdminModel.class);

                            if(adminModel.getUseremail().equalsIgnoreCase(mGetValue.getString("useremail"))){
                               String s = snapshot.getKey().toString();
                                FirebaseDatabase.getInstance().getReference().child("users").
                                        child(s).setValue(null);

                                Intent intent = new Intent(AdminDetail.this, CurrentAdmins.class);
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
                

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(){
        imageView = findViewById(R.id.admin_profile_pic_detail);
        mFirstNameTv = findViewById(R.id.firstName_tv);
        mFatherNameTv = findViewById(R.id.fatherName_tv);
        mCnicNumberTv = findViewById(R.id.cnic_tv);
        mPhoneNumberTv = findViewById(R.id.phoneNumber_tv);
        mEmailTv = findViewById(R.id.email_tv);
        mPasswordTv = findViewById(R.id.password_tv);
        mCnicPicture = findViewById(R.id.Cnic_img_view);
        mGetValue = getIntent().getExtras();
    }

    public void onBackPressed() {
        finish();
    }
}