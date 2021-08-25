package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ScreenOnOffBackgroundService extends Service {
    private ScreenOnOffReceiver screenOnOffReceiver=null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onCreate(){
        super.onCreate();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.setPriority(100);
        screenOnOffReceiver = new ScreenOnOffReceiver();
        registerReceiver(screenOnOffReceiver, intentFilter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister screenOnOffReceiver when destroy.
        if(screenOnOffReceiver!=null)
        {
            unregisterReceiver(screenOnOffReceiver);
        }
    }
}

