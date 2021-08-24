package com.example.moneytransfer.activities;

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
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.activities.AdminHome;
import com.example.moneytransfer.activities.activities.Home;
import com.example.moneytransfer.activities.activities.LoginScreen;
import com.example.moneytransfer.activities.models.AdminModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;


    boolean netConnection;
    FirebaseAuth mAuth;
    List<AdminModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        checkConnection();

        if(netConnection == true){
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(currentUser == null) {

                        Intent intent=new Intent(MainActivity.this, LoginScreen.class);
                        startActivity(intent);
                        finish();

                    }

                    else if (currentUser.getEmail().equalsIgnoreCase("Shahabafridi11@gmail.com")) {
                        Intent splashIntent = new Intent(MainActivity.this, AdminHome.class);

                        FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot,
                                                     @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                                AdminModel adminModel = snapshot.getValue(AdminModel.class);
//                                list.add(adminModel);
//                                int i = list.indexOf("useremail");
//                                String s = list.get(i).toString();

                                if(adminModel.getUseremail().equalsIgnoreCase("Shahabafridi11@gmail.com")){
                                    splashIntent.putExtra("name",adminModel.getFirstname()+" "+adminModel.getSecondname());
                                    splashIntent.putExtra("useremail",adminModel.getUseremail());
                                    splashIntent.putExtra("fathername",adminModel.getFathername());
                                    splashIntent.putExtra("cnicnumber",adminModel.getCnicnumber());
                                    splashIntent.putExtra("cnicpath",adminModel.getCnicpath());
                                    splashIntent.putExtra("profilepic",adminModel.getProfilepath());
                                    splashIntent.putExtra("phonenumber",adminModel.getPhonenumber());
                                    splashIntent.putExtra("date",adminModel.getDate());
                                    splashIntent.putExtra("password",adminModel.getPassword());
                                    splashIntent.putExtra("branch",adminModel.getBranchname());

                                    startActivity(splashIntent);
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

                    else {
                        Intent intent = new Intent(MainActivity.this, Home.class);


                        FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot,
                                                     @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {


                                AdminModel adminModel = snapshot.getValue(AdminModel.class);
//                                list.add(adminModel);
//                                int i = list.indexOf("useremail");
//                                String s = list.get(i).toString();


                                if(adminModel.getUseremail().equalsIgnoreCase(currentUser.getEmail())){
                                    Toast.makeText(MainActivity.this, "Matched", Toast.LENGTH_SHORT).show();
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
                                }else {
                                    Toast.makeText(MainActivity.this, "Not MAtched", Toast.LENGTH_SHORT).show();
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
            },2000);
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setIcon(R.drawable.exit_app);
            dialog.setTitle("Check Your Intrenet Connection");
            dialog.setMessage("Do You Want To Exit The App");
            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            dialog.create();
            dialog.show();
        }


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