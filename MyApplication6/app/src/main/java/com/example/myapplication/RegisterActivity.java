package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout editTextEmail;
    private TextInputLayout editTextPassword;
    private TextInputLayout editTextPasswordagain;
    private FirebaseAuth firebaseAuth;
    private String Userid;
    String email;
    String password,passwordagain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
            Button buttonRegister = findViewById(R.id.buttonRegister);
            editTextEmail=findViewById(R.id.editTextEmail);
            editTextPassword=findViewById(R.id.editTextPassword);
            editTextPasswordagain=findViewById(R.id.editTextPasswordAgain);
            TextView textViewLogin =findViewById(R.id.textViewLogin);
            firebaseAuth=FirebaseAuth.getInstance();
            textViewLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
                }
            });
            buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email=editTextEmail.getEditText().getText().toString();
                    password=editTextPassword.getEditText().getText().toString();
                    passwordagain=editTextPasswordagain.getEditText().getText().toString();
                    if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordagain)){
                        Toast.makeText(RegisterActivity.this, "Please enter the email and password", Toast.LENGTH_LONG).show();
                    }
                    else if(password.length()<6 || passwordagain.length()<6){
                        Toast.makeText(RegisterActivity.this, "Password must be greater than 6 letters", Toast.LENGTH_SHORT).show();
                    }

                    else if(!password.equals(passwordagain)) {
                        Toast.makeText(RegisterActivity.this, "Passwords are not equal", Toast.LENGTH_LONG).show();
                    }
                    else{
                        registerUser(email,password);
                    }
                }
            });
        }
        private void registerUser(final String email, final String password) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Registering Successful",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this,ProfileActivity.class));
                        Userid=firebaseAuth.getUid();
                        Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("Password",password);
                        intent.putExtra("UserID",Userid);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,"Registering Not Successful",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

