package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.MainActivity;
import com.example.moneytransfer.activities.models.AdminModel;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class LoginScreen extends AppCompatActivity {

    private TextView signUpTv;
    private Button loginBtn;
    private EditText userNameEt, passwordEt;

    private String userEmail,
            userPassword;

    SpinKitView regProgress;
    boolean netConnection;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        init();
        checkConnection();
        mAuth = FirebaseAuth.getInstance();
        regProgress.setVisibility(View.GONE);

//        loginBtn.getBackground().setAlpha(100);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regProgress.setVisibility(View.VISIBLE);
                loginUser();
            }
        });

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loginUser(){
        userEmail = userNameEt.getText().toString().trim();
        userPassword = passwordEt.getText().toString().trim();


        if(userEmail.isEmpty()){
            userNameEt.setError("Email Required");
            regProgress.setVisibility(View.GONE);
        }else if(userPassword.isEmpty()){
            passwordEt.setError("password Required");
            regProgress.setVisibility(View.GONE);
        }else if (userPassword.length() < 6) {
            passwordEt.setError("At least 6 character password");
            regProgress.setVisibility(View.GONE);
        }else if ((!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())) {
            userNameEt.setError("Wrong format");
            regProgress.setVisibility(View.GONE);
        }else if(netConnection){
            // todo user login

            mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if(userEmail.equalsIgnoreCase("Shahabafridi11@gmail.com")){
                            Intent intent = new Intent(LoginScreen.this, AdminHome.class);

                            FirebaseDatabase.getInstance().getReference().child("users").
                                    addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot,
                                                         @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                                    AdminModel adminModel = snapshot.getValue(AdminModel.class);
//                                list.add(adminModel);
//                                int i = list.indexOf("useremail");
//                                String s = list.get(i).toString();

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

                                        startActivity(intent);
                                        finish();
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
                        } else {
                            Intent intent = new Intent(LoginScreen.this, Home.class);

                            final FirebaseUser currentUser = mAuth.getCurrentUser();

                            FirebaseDatabase.getInstance().getReference().child("users").
                                    addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot,
                                                         @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {


                                    AdminModel adminModel = snapshot.getValue(AdminModel.class);
//                                list.add(adminModel);
//                                int i = list.indexOf("useremail");
//                                String s = list.get(i).toString();


                                    if(adminModel.getUseremail().equalsIgnoreCase(currentUser.getEmail())){
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

                                        startActivity(intent);
                                        finish();
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
                }
            }).addOnFailureListener(LoginScreen.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    regProgress.setVisibility(View.GONE);
                    Toast.makeText(LoginScreen.this, "Wrong Email Or Password", Toast.LENGTH_SHORT).show();
                }
            });

//            mAuth.signInWithEmailAndPassword(userEmail,userPassword).
//                    addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                               // if(userEmail.equalsIgnoreCase("Shahabafridi11@gmail.com")){
//                                    startActivity(new Intent(LoginScreen.this, AdminHome.class));
//                                    finish();
//                              //  }else {
//                              //      startActivity(new Intent(LoginScreen.this, Home.class));
//                              //      finish();
//                               // }
//
//
//                            }else{
//                                String error = task.getException().toString();
//                                Toast.makeText(LoginScreen.this, error, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });


        }
    }



    private void init() {
        loginBtn = findViewById(R.id.Login_btn);
        signUpTv = findViewById(R.id.SignUp_tv);
        userNameEt = findViewById(R.id.Username_et);
        passwordEt = findViewById(R.id.Password_et);
        regProgress = findViewById(R.id.login_progress);
        Sprite wave = new Wave();
        regProgress.setIndeterminateDrawable(wave);
    }

    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginScreen.this);
        dialog.setIcon(R.drawable.exit_app);
        dialog.setTitle("Exit Application");
        dialog.setMessage("Do You Want To Exit The App");
        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.create();
        dialog.show();

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
}