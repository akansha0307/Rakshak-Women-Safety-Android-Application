package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class ScreenOnOffReceiver extends BroadcastReceiver {
    int count=0;
    public static long startTime;
    public static long differenceTime=0;
    private final static String SCREEN_TOGGLE_TAG="SCREEN_TOGGLE_TAG";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                Log.i(SCREEN_TOGGLE_TAG, "Screen is turned off");
                count++;
            } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                Log.i(SCREEN_TOGGLE_TAG, "Screen is turned on");
                count++;
            }
            if(count==1){
                startTime=System.currentTimeMillis();
                differenceTime=System.currentTimeMillis()-startTime;
                if(differenceTime>3000){
                    count=0;
                }

            }
        else if(count==3)
        {
            differenceTime=System.currentTimeMillis()-startTime;
            Log.i(SCREEN_TOGGLE_TAG,"3 times");
            if(differenceTime<=3000)
            {
                Log.i(SCREEN_TOGGLE_TAG,"Triggered Alarm");
                Intent i=new Intent(context,GPS.class);
                context.startService(i);
                count=0;
            }
            else{
                count=0;
            }
        }
        else if(count==2){
            differenceTime=System.currentTimeMillis()-startTime;
            if(differenceTime>3000){
                count=0;
            }
            }

    }

}
