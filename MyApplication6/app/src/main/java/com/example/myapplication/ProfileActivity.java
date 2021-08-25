package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.jar.Attributes;

public class ProfileActivity extends AppCompatActivity {
    private TextInputLayout editTextName;
    private TextInputLayout editTextPhone1;
    private TextInputLayout editTextPhone2;
    private TextInputLayout editTextPhone3;
    private Button buttonSave;
    String Name;
    String EC1;
    String EC2;
    String EC3;
    String UserID;
    String Email;
    String Password;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editTextName=findViewById(R.id.editTextName);
        editTextPhone1=findViewById(R.id.editTextPhone1);
        editTextPhone2=findViewById(R.id.editTextPhone2);
        editTextPhone3=findViewById(R.id.editTextPhone3);
        buttonSave=(Button)findViewById(R.id.buttonAddPeople);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=editTextName.getEditText().getText().toString();
                EC1=editTextPhone1.getEditText().getText().toString();
                EC2=editTextPhone2.getEditText().getText().toString();
                EC3=editTextPhone3.getEditText().getText().toString();
                Intent i=getIntent();
                Email=i.getStringExtra("Email");
                UserID=i.getStringExtra("UserID");
                Password=i.getStringExtra("Password");
                sharedPreferences=ProfileActivity.this.getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("EC1",EC1).apply();
                sharedPreferences.edit().putString("EC2",EC2).apply();
                sharedPreferences.edit().putString("EC3",EC3).apply();
                sharedPreferences.edit().putString("Email",Email).apply();
                sharedPreferences.edit().putString("UserId",UserID).apply();
                sharedPreferences.edit().putString("Name",Name).apply();
                sharedPreferences.edit().putString("Password",Password).apply();
                HashMap<String,Object> map=new HashMap<>();
                map.put("Emergency Contact3",EC3);
                map.put("Emergency Contact2",EC2);
                map.put("Emergency Contact1",EC1);
                map.put("Password",Password);
                map.put("Email",Email);
                map.put("Name",Name);
                if(Name.isEmpty() || EC1.isEmpty()||EC2.isEmpty()||EC3.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter the details", Toast.LENGTH_SHORT).show();
                }
                {
                    FirebaseDatabase.getInstance().getReference().child("Users").child(UserID).updateChildren(map);
                    Intent intent=new Intent(getApplicationContext(),Homepage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

