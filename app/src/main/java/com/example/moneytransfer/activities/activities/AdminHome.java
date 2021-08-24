package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moneytransfer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference databaseReferenceBranch;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
   // private FirebaseAuth mAuth;
   // String currentUser;

    NavigationView navigationView;
    private DrawerLayout drawer;
    Singleton ls = Singleton.getInstance();

    private ImageView pImage;
    private  TextView pName, pEamil;

    LinearLayout mGenerateTransaction, mPayTransaction, mTransactions, mExchange;

    private Bundle mGetValue;
    private String cnicPath, profilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        mAuth = FirebaseAuth.getInstance();
        databaseReferenceBranch = FirebaseDatabase.getInstance().getReference().child("branches");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);


        toggle.syncState();



        if (savedInstanceState == null) {
            hideKeyboard(this);


        }

        init();
        getAndSetIntentValues();

//        if(toggle.isDrawe){
//            Glide.with(AdminHome.this).load(profilePath).into(pImage);
//        }



        mGenerateTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, GenerateTransaction.class);
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

        mPayTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, PayTransaction.class);
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

        mTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, Transactions.class);
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

        mExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, Exchange.class);
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

    private void init() {

        mGetValue = getIntent().getExtras();
        mGenerateTransaction = findViewById(R.id.generate_transaction);
        mGenerateTransaction.getBackground().setAlpha(60);
        mPayTransaction = findViewById(R.id.pay_transaction);
        mPayTransaction.getBackground().setAlpha(60);
        mTransactions = findViewById(R.id.transactions);
        mTransactions.getBackground().setAlpha(60);
        mExchange= findViewById(R.id.Exchange_rates);
        mExchange.getBackground().setAlpha(60);
        pImage = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        pName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.profile_name);
        pEamil = (TextView) navigationView.getHeaderView(0).findViewById(R.id.profile_email_d);


        profilePath = mGetValue.getString("profilepic");

        Glide.with(AdminHome.this).load(profilePath).into(pImage);
        pName.setText(mGetValue.getString("name"));
        pEamil.setText(mGetValue.getString("useremail"));


 //       Toast.makeText(this, mGetValue.getString("branch"), Toast.LENGTH_SHORT).show();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.home_screen_menu_sa, menu);
//        return true;
//    }

    //Below Code is For Option Menu

 //   @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.profile:
//                mGetValue = getIntent().getExtras();
////                Toast.makeText(this, mGetValue.getString("username"), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(AdminHome.this, Profile.class);
//                intent.putExtra("name",mGetValue.getString("name"));
//                intent.putExtra("useremail",mGetValue.getString("useremail"));
//                intent.putExtra("fathername",mGetValue.getString("fathername"));
//                intent.putExtra("cnicnumber",mGetValue.getString("cnicnumber"));
//                intent.putExtra("cnicpath",mGetValue.getString("cnicpath"));
//                intent.putExtra("profilepic",mGetValue.getString("profilepic"));
//                intent.putExtra("phonenumber",mGetValue.getString("phonenumber"));
//                intent.putExtra("date",mGetValue.getString("date"));
//                intent.putExtra("password", mGetValue.getString("password"));
//                intent.putExtra("branch",mGetValue.getString("branch"));
//                startActivity(intent);
//                finish();
//                return true;
//
//            case R.id.logout:
//                currentUser = mAuth.getCurrentUser();
//                mAuth.signOut();
//                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
//
//                Intent intent1 = new Intent(AdminHome.this, LoginScreen.class);
//                startActivity(intent1);
//                finish();
//                return true;
//
//            case R.id.admin_control:
//                Intent intent2 = new Intent(AdminHome.this, AdminsControl.class);
//                intent2.putExtra("name",mGetValue.getString("name"));
//                intent2.putExtra("useremail",mGetValue.getString("useremail"));
//                intent2.putExtra("fathername",mGetValue.getString("fathername"));
//                intent2.putExtra("cnicnumber",mGetValue.getString("cnicnumber"));
//                intent2.putExtra("cnicpath",mGetValue.getString("cnicpath"));
//                intent2.putExtra("profilepic",mGetValue.getString("profilepic"));
//                intent2.putExtra("phonenumber",mGetValue.getString("phonenumber"));
//                intent2.putExtra("date",mGetValue.getString("date"));
//                intent2.putExtra("password", mGetValue.getString("password"));
//                intent2.putExtra("branch",mGetValue.getString("branch"));
//                startActivity(intent2);
//                finish();
//                return true;
//
//            case R.id.admins:
//                Intent intent3 = new Intent(AdminHome.this, CurrentAdmins.class);
//                startActivity(intent3);
//                finish();
//                return true;
//
//            case R.id.update_exchange_rates:
//                Intent intent4 = new Intent(AdminHome.this, UpdateExchangeRates.class);
//                startActivity(intent4);
//                finish();
//                return true;
//
//            case R.id.add_branch:
//
//                final DialogPlus dialogPlus=DialogPlus.newDialog(AdminHome.this)
//                        .setContentHolder(new ViewHolder(R.layout.add_branch))
//                        .setExpanded(true,700)
//                        .create();
//
//                View myview=dialogPlus.getHolderView();
//                EditText mbranchEt=myview.findViewById(R.id.branch_name_et);
//                Button mAddBranchBtn=myview.findViewById(R.id.add_branch);
//
//                dialogPlus.show();
//
//                mAddBranchBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String branch = mbranchEt.getText().toString().trim();
//
//                        if(!branch.equals("")){
//                            databaseReferenceBranch.push().setValue(branch).
//                                    addOnCompleteListener(AdminHome.this, new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                            dialogPlus.dismiss();
//                                            Toast.makeText(AdminHome.this, "Branch Successfully Added", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        } else {
//                            mbranchEt.setError("Enter Branch Name");
//                        }
//
//                    }
//                });
//
//                return true;
//
//
//            case R.id.exit_application:
//                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminHome.this);
//                dialog.setIcon(R.drawable.exit_app);
//                dialog.setTitle("Exit Application");
//                dialog.setMessage("Do You Want To Exit The App");
//                dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//                dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                dialog.create();
//                dialog.show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void getAndSetIntentValues() {
        mGetValue = getIntent().getExtras();

//        Toast.makeText(this, mGetValue.getString("cnicnumber"), Toast.LENGTH_SHORT).show();
//        cnicpath = mGetValue.getString("cnicpath");
//
//        mEmail.setText("Enail: "+mGetValue.getString("useremail"));
//        Toast.makeText(this, mGetValue.getString("useremail"), Toast.LENGTH_SHORT).show();
//        mFatherNameTv.setText("Father Name: " + mGetValue.getString("fathername"));
//        mCnicNumberTv.setText("Cnic: " + mGetValue.getString("cnicnumber"));
//        mPhoneNumberTv.setText("Phone Number: " + mGetValue.getString("phonenumber"));
//        mEmailTv.setText("Email: " + mGetValue.getString("useremail"));
//        mPasswordTv.setText("Password: " + mGetValue.getString("password"));


        //todo for loading image from path

//        Glide.with(AdminDetail.this).load(profileimagePath).into(imageView);
//        Glide.with(AdminDetail.this).load(cnicpath).into(mCnicPicture);
    }

    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AdminHome.this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                mGetValue = getIntent().getExtras();
//                Toast.makeText(this, mGetValue.getString("username"), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AdminHome.this, Profile.class);
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
                return true;

            case R.id.logout:
                currentUser = mAuth.getCurrentUser();
                mAuth.signOut();
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(AdminHome.this, LoginScreen.class);
                startActivity(intent1);
                finish();
                return true;

            case R.id.admin_control:
                Intent intent2 = new Intent(AdminHome.this, AdminsControl.class);
                intent2.putExtra("name",mGetValue.getString("name"));
                intent2.putExtra("useremail",mGetValue.getString("useremail"));
                intent2.putExtra("fathername",mGetValue.getString("fathername"));
                intent2.putExtra("cnicnumber",mGetValue.getString("cnicnumber"));
                intent2.putExtra("cnicpath",mGetValue.getString("cnicpath"));
                intent2.putExtra("profilepic",mGetValue.getString("profilepic"));
                intent2.putExtra("phonenumber",mGetValue.getString("phonenumber"));
                intent2.putExtra("date",mGetValue.getString("date"));
                intent2.putExtra("password", mGetValue.getString("password"));
                intent2.putExtra("branch",mGetValue.getString("branch"));
                startActivity(intent2);
                finish();
                return true;

            case R.id.admins:
                Intent intent3 = new Intent(AdminHome.this, CurrentAdmins.class);
                intent3.putExtra("name",mGetValue.getString("name"));
                intent3.putExtra("useremail",mGetValue.getString("useremail"));
                intent3.putExtra("fathername",mGetValue.getString("fathername"));
                intent3.putExtra("cnicnumber",mGetValue.getString("cnicnumber"));
                intent3.putExtra("cnicpath",mGetValue.getString("cnicpath"));
                intent3.putExtra("profilepic",mGetValue.getString("profilepic"));
                intent3.putExtra("phonenumber",mGetValue.getString("phonenumber"));
                intent3.putExtra("date",mGetValue.getString("date"));
                intent3.putExtra("password", mGetValue.getString("password"));
                intent3.putExtra("branch",mGetValue.getString("branch"));
                startActivity(intent3);
                finish();
                return true;

            case R.id.update_exchange_rates:
                Intent intent4 = new Intent(AdminHome.this, UpdateExchangeRates.class);
                intent4.putExtra("name",mGetValue.getString("name"));
                intent4.putExtra("useremail",mGetValue.getString("useremail"));
                intent4.putExtra("fathername",mGetValue.getString("fathername"));
                intent4.putExtra("cnicnumber",mGetValue.getString("cnicnumber"));
                intent4.putExtra("cnicpath",mGetValue.getString("cnicpath"));
                intent4.putExtra("profilepic",mGetValue.getString("profilepic"));
                intent4.putExtra("phonenumber",mGetValue.getString("phonenumber"));
                intent4.putExtra("date",mGetValue.getString("date"));
                intent4.putExtra("password", mGetValue.getString("password"));
                intent4.putExtra("branch",mGetValue.getString("branch"));
                startActivity(intent4);
                finish();
                return true;

            case R.id.add_branch:

                final DialogPlus dialogPlus=DialogPlus.newDialog(AdminHome.this)
                        .setContentHolder(new ViewHolder(R.layout.add_branch))
                        .setExpanded(true,700)
                        .create();

                View myview=dialogPlus.getHolderView();
                EditText mbranchEt=myview.findViewById(R.id.branch_name_et);
                Button mAddBranchBtn=myview.findViewById(R.id.add_branch);

                dialogPlus.show();

                mAddBranchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String branch = mbranchEt.getText().toString().trim();

                        if(!branch.equals("")){
                            databaseReferenceBranch.push().setValue(branch).
                                    addOnCompleteListener(AdminHome.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            dialogPlus.dismiss();
                                            Toast.makeText(AdminHome.this, "Branch Successfully Added", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            mbranchEt.setError("Enter Branch Name");
                        }

                    }
                });

                return true;


            case R.id.exit_application:
                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminHome.this);
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
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}