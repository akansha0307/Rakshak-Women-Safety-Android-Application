package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenOnOffActivity extends AppCompatActivity {
private ScreenOnOffReceiver screenOnOffReceiver=null;
private static int time=10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=(TextView)findViewById(R.id.NametextView);
        textView.animate().alpha(0).setDuration(10000);
        ImageView imageView = (ImageView) findViewById(R.id.LogoView);
        imageView.animate().alpha(0).setDuration(10000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(ScreenOnOffActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, time);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.setPriority(100);
        screenOnOffReceiver=new ScreenOnOffReceiver();
        registerReceiver(screenOnOffReceiver,intentFilter);
        Intent backgroundService = new Intent(getApplicationContext(), ScreenOnOffBackgroundService.class);
        startService(backgroundService);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(screenOnOffReceiver!=null)
        {
            unregisterReceiver(screenOnOffReceiver);
        }
    }
}

