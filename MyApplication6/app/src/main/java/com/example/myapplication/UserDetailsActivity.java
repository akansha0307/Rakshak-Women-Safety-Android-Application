package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;

public class UserDetailsActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    TextInputLayout Name,Email,EC1,EC2,EC3,Password;
    String name,phone1,phone2,phone3,email,password;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Name=findViewById(R.id.editTextName);
        Email=findViewById(R.id.editTextEmail);
        EC1=findViewById(R.id.editTextPhone1);
        EC2=findViewById(R.id.editTextPhone2);
        EC3=findViewById(R.id.editTextPhone3);
        Password=findViewById(R.id.editTextPassword);
        showData();
    }
    public void showData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
        phone1 = sharedPreferences.getString("EC1", "");
        phone2 = sharedPreferences.getString("EC2", "");
        phone3 = sharedPreferences.getString("EC3", "");
        name = sharedPreferences.getString("Name", "");
        email = sharedPreferences.getString("Email", "");
        UserId = sharedPreferences.getString("UserId", "");
        password=sharedPreferences.getString("Password","");
        Log.i("Myfilter", UserId);
        EC1.getEditText().setText(phone1);
        EC2.getEditText().setText(phone2);
        EC3.getEditText().setText(phone3);
        Name.getEditText().setText(name);
        Email.getEditText().setText(email);
        Password.getEditText().setText(password);
    }

    public void update(View view){
        if (isNameChanged() || isPasswordChanged() || isPhone1Changed() || isPhone2Changed() || isPhone3Changed() || isEmailChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Data not changed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmailChanged() {
        if(!email.equals(Email.getEditText().getText().toString())){
            databaseReference.child(UserId).child("Email").setValue(Email.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isPhone3Changed() {
        if(!phone3.equals(EC3.getEditText().getText().toString())){
            databaseReference.child(UserId).child("Emergency Contact3").setValue(EC3.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }


    private boolean isPhone2Changed() {
        if(!phone2.equals(EC2.getEditText().getText().toString())){
            databaseReference.child(UserId).child("Emergency Contact2").setValue(EC2.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }


    private boolean isPhone1Changed() {
        if(!phone1.equals(EC1.getEditText().getText().toString())){
            databaseReference.child(UserId).child("Emergency Contact1").setValue(EC1.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }


    private boolean isNameChanged() {
     if(!name.equals(Name.getEditText().getText().toString())){
         databaseReference.child(UserId).child("Name").setValue(Name.getEditText().getText().toString());
         return true;
     }
     else{
         return false;
     }
    }
    private boolean isPasswordChanged() {
        if(!password.equals(Password.getEditText().getText().toString())){
            databaseReference.child(UserId).child("Password").setValue(Password.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }
}
