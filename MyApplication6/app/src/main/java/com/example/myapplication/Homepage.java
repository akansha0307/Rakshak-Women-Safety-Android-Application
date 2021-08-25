package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.SEND_SMS;
public class Homepage extends AppCompatActivity {
TextView textViewhelpline,textViewNGO,textViewTips,textViewVideos,textViewLaws;
ImageView imageViewhelpline,imageViewNGO,imageViewTips,imageViewVideos,imageViewLaws;
    public static final int RequestPermissionCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        textViewVideos=findViewById(R.id.textViewVideos);
        textViewhelpline=findViewById(R.id.textViewHelpline);
        textViewLaws=findViewById(R.id.textViewLaws);
        textViewNGO=findViewById(R.id.textViewNGO);
        textViewTips=findViewById(R.id.textViewTips);
        imageViewVideos=findViewById(R.id.imageViewVideos);
        imageViewhelpline=findViewById(R.id.imageViewHelpline);
        imageViewLaws=findViewById(R.id.imageViewLaws);
        imageViewNGO=findViewById(R.id.imageViewNGO);
        imageViewTips=findViewById(R.id.imageViewTips);
        textViewVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,VideosActivity.class);
                startActivity(i);
            }
        });
        imageViewVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,VideosActivity.class);
                startActivity(i);
            }
        });
        textViewLaws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,LawsActivity.class);
                startActivity(i);
            }
        });
        imageViewLaws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,LawsActivity.class);
                startActivity(i);
            }
        });
        textViewhelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,HelplineActivity.class);
                startActivity(i);
            }
        });
        imageViewhelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,HelplineActivity.class);
                startActivity(i);
            }
        });
        textViewTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,TipsActivity.class);
                startActivity(i);
            }
        });
        imageViewTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,TipsActivity.class);
                startActivity(i);
            }
        });
        textViewNGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,NGOActivity.class);
                startActivity(i);
            }
        });
        imageViewNGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,NGOActivity.class);
                startActivity(i);
            }
        });
        requestPermission();
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(Homepage.this, new String[]{ACCESS_FINE_LOCATION,SEND_SMS}, RequestPermissionCode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean LocationPermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean SMSPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;
                }
                break;
        }
    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),SEND_SMS);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.homepage_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.UserDetails){
            Intent i=new Intent(Homepage.this,UserDetailsActivity.class);
            startActivity(i);
        }
        else if(id==R.id.Logout){
            SharedPreferences sharedPreferences1=getSharedPreferences("checkbox",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences1.edit();
            editor.putString("Remember","false");
            editor.apply();
            Intent i=new Intent(Homepage.this,LoginActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
