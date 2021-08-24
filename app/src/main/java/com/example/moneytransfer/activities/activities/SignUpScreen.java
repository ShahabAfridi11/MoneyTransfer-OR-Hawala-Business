package com.example.moneytransfer.activities.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytransfer.R;
import com.example.moneytransfer.activities.models.AdminModel;
import com.example.moneytransfer.activities.models.BranchesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class SignUpScreen extends AppCompatActivity {

    private EditText mFirstNameEt, mSecondNameEt, mFatherNameEt, mEmailEt, mPasswordEt, mConfirmPasswordEt;
    private MaskedEditText mCnicEt, mPhoneNumberEt;
    private Button mUploadCnic, mUploadProfile, mSignUp;
    private ImageView mCnicIMG, mProfilePictureIMG;
    private Spinner mBranches;

    ArrayList<String> list;
    ValueEventListener valueEventListener;
    ArrayAdapter<String> arrayAdapter;

    private String firstName, secondName, fatherName,
            cnic, branchName, phoneNumber, email, cnicIMGPath,
            profileIMGPath, password, confirmPassword, requestedDate;

    boolean netConnection;

    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        init();
        checkConnection();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReferenceSpinner = FirebaseDatabase.getInstance().getReference("branches");

        list = new ArrayList<>();
        list.clear();
        list.add("Select Branch");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        mBranches.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        addBranchesToSpinner();


    }

    public void selectCnicImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,0);
    }

    public void selectProfileImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    public void addBranchesToSpinner() {
        valueEventListener = databaseReferenceSpinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot myData : snapshot.getChildren()){
                    if(myData.getValue().equals("Owner")){

                    }else{
                        list.add(myData.getValue().toString());
                        arrayAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void requestUser(View view) {
        firstName = mFirstNameEt.getText().toString().trim();
        secondName = mSecondNameEt.getText().toString().trim();
        fatherName = mFatherNameEt.getText().toString();
        cnic = mCnicEt.getText().toString().trim();
        branchName = mBranches.getSelectedItem().toString();
        phoneNumber = mPhoneNumberEt.getText().toString().trim();
        email = mEmailEt.getText().toString().trim().trim();
        password = mPasswordEt.getText().toString().trim();
        confirmPassword = mConfirmPasswordEt.getText().toString().trim();
        requestedDate =new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if(netConnection) {


            if (firstName.equals("")) {
                mFirstNameEt.setError("Name required!");
            } else if (secondName.equals("")) {
                mSecondNameEt.setError("Email required!");
            } else if (fatherName.equals("")) {
                mFatherNameEt.setError("Father Name Required!");
            } else if (cnic.length() < 15) {
                mCnicEt.setError("correct and complete cnic required!");
            } else if (phoneNumber.length() < 12) {
                mPhoneNumberEt.setError("corred and complete phone number");
            } else if ((!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                mEmailEt.setError("Wrong format");
            } else if (password.equals("") && !(password.length() < 6)) {
                mCnicEt.setError("At least 6 character password");
            } else if (!(password.equals(confirmPassword))) {
                mConfirmPasswordEt.setError("password not match");
            }else if (branchName.equals("Select Branch")) {
                ((TextView)mBranches.getSelectedView()).setError("Select Branch Please");
            } else {

                if (!cnicIMGPath.equals("") && !profileIMGPath.equals("")) {
                    HashMap<String, String> myData = new HashMap<String, String>();
                    myData.put("firstname", firstName);
                    myData.put("secondname", secondName);
                    myData.put("fathername", fatherName);
                    myData.put("cnicnumber", cnic);
                    myData.put("branchname", branchName);
                    myData.put("phonenumber", phoneNumber);
                    myData.put("useremail", email);
                    myData.put("password", confirmPassword);
                    myData.put("date", requestedDate);
                    myData.put("cnicpath", cnicIMGPath);
                    myData.put("profilepath", profileIMGPath);


                    databaseReference.child("requestedusers").child(firstName).setValue(myData).
                            addOnCompleteListener(SignUpScreen.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpScreen.this, "Request is Add", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpScreen.this, LoginScreen.class));
                                finish();
                            }
                        }
                    });
                } else if (cnicIMGPath.equals("") || profileIMGPath.equals("")) {
                    Toast.makeText(this, "Wait image is uploading and press agaim signup", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    mCnicIMG.setImageURI(selectedImage);

                    mStorageRef.child("images/").child(System.currentTimeMillis()+".jpg").
                            putFile(selectedImage).
                            addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    if (taskSnapshot.getMetadata() != null) {
                                        if (taskSnapshot.getMetadata().getReference() != null) {
                                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    cnicIMGPath = uri.toString();
                                                    Toast.makeText(SignUpScreen.this, "Path selected", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{
                                            cnicIMGPath="no_image";
                                            Toast.makeText(SignUpScreen.this, "Path not selected", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    Toast.makeText(SignUpScreen.this, "image uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                break;

            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    mProfilePictureIMG.setImageURI(selectedImage);

                    mStorageRef.child("images/").child(System.currentTimeMillis()+".jpg").
                            putFile(selectedImage).
                            addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    if (taskSnapshot.getMetadata() != null) {
                                        if (taskSnapshot.getMetadata().getReference() != null) {
                                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    profileIMGPath = uri.toString();
                                                    Toast.makeText(SignUpScreen.this, "Path selected", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{
                                            profileIMGPath="no_image";
                                            Toast.makeText(SignUpScreen.this, "Path not selected", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    Toast.makeText(SignUpScreen.this, "image uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                break;

        }
    }

    private void init() {
        mFirstNameEt = findViewById(R.id.firstName_et);
        mSecondNameEt = findViewById(R.id.secondName_et);
        mFatherNameEt = findViewById(R.id.fatherName_et);
        mEmailEt = findViewById(R.id.email_et);
        mPasswordEt = findViewById(R.id.password_et);
        mConfirmPasswordEt = findViewById(R.id.confirm_Password_et);
        mCnicEt = findViewById(R.id.cnic_et);
        mPhoneNumberEt = findViewById(R.id.phoneNumber_et);
        mCnicIMG = findViewById(R.id.Cnic_img);
        mProfilePictureIMG = findViewById(R.id.profile_img);

        mBranches = findViewById(R.id.branches_list);

        


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
        Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }
}