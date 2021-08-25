package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private FirebaseAuth auth;
    TextInputLayout email,password;
    TextView textView;
    String UId;
    String nameFromDB,EC1fromDB,EC2fromDB,EC3fromDB;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin=findViewById(R.id.button);
        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        checkBox=findViewById(R.id.checkBoxRememberMe);
        textView=findViewById(R.id.TextViewRegister);
        SharedPreferences sharedPreferences1=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=sharedPreferences1.getString("Remember","");
        if(checkbox.equals("true")){
            Intent intent=new Intent(LoginActivity.this,Homepage.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }
        auth=FirebaseAuth.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getEditText().getText().toString();
                String txt_password = password.getEditText().getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_email , txt_password);
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences sharedPreferences1=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences1.edit();
                    editor.putString("Remember","true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"Checked",Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences sharedPreferences1=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences1.edit();
                    editor.putString("Remember","false");
                    editor.apply();
                }
            }
        });
    }
    private void loginUser(final String email, final String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                UId=auth.getUid();
                Log.i("Myfilter",UId);
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
                Query checkUser=reference.orderByChild("Email").equalTo(email);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String passwordFromDB=dataSnapshot.child(UId).child("Password").getValue().toString();
                            if(passwordFromDB.equals(password)){
                                nameFromDB=dataSnapshot.child(UId).child("Name").getValue().toString();
                                EC1fromDB=dataSnapshot.child(UId).child("Emergency Contact1").getValue().toString();
                                EC2fromDB=dataSnapshot.child(UId).child("Emergency Contact2").getValue().toString();
                                EC3fromDB=dataSnapshot.child(UId).child("Emergency Contact3").getValue().toString();
                                sharedPreferences=LoginActivity.this.getSharedPreferences("com.example.myapplication",Context.MODE_PRIVATE);
                                sharedPreferences.edit().putString("EC1",EC1fromDB).apply();
                                sharedPreferences.edit().putString("EC2",EC2fromDB).apply();
                                sharedPreferences.edit().putString("EC3",EC3fromDB).apply();
                                sharedPreferences.edit().putString("Email",email).apply();
                                sharedPreferences.edit().putString("UserId",UId).apply();
                                sharedPreferences.edit().putString("Name",nameFromDB).apply();
                                sharedPreferences.edit().putString("Password",passwordFromDB).apply();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(LoginActivity.this,Homepage.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void register(){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    public void ScreenOnOffReceiver(View view){
        Intent i=new Intent(LoginActivity.this,ScreenOnOffReceiver.class);
    }
    }

