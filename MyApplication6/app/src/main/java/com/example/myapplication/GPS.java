package com.example.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.Nullable;


public class GPS extends Service implements LocationListener{
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude,longitude;
    LocationManager locationManager;
    Location location;
    private final static String SCREEN_TOGGLE_TAG="SCREEN_TOGGLE_TAG";
    public static String str_receiver = "servicetutorial.service.receiver";
    Intent intent;
    String phone1,phone2,phone3;
    private MediaPlayer mediaPlayer;
    private static int time=5000;
    public GPS() {
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(str_receiver);
        getlocation();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void getlocation(){
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable){

        }else {

            if (isNetworkEnable){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null){

                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        updatelocation(location);
                    }
                }

            }


            if (isGPSEnable){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        updatelocation(location);
                    }
                }
            }


        }

    }
    private void setMediaVolumeMax() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(3);
        audioManager.setStreamVolume(3, maxVolume, 1);
    }

    private void updatelocation(Location location){
        setMediaVolumeMax();
        mediaPlayer = MediaPlayer.create(this,R.raw.scream);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        }, time);
        intent.putExtra("latutide",location.getLatitude()+"");
        intent.putExtra("longitude",location.getLongitude()+"");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
        phone1 = sharedPreferences.getString("EC1", "");
        Log.i(SCREEN_TOGGLE_TAG,phone1);
        phone2 = sharedPreferences.getString("EC2", "");
        Log.i(SCREEN_TOGGLE_TAG,phone2);
        phone3 = sharedPreferences.getString("EC3", "");
        Log.i(SCREEN_TOGGLE_TAG,phone3);
        String loc="https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone1, null,"Need help!!My location is:"+loc, null, null);
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone2, null,"Need help!!My location is:"+loc, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone3, null,"Need help!!My location is:"+loc, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendBroadcast(intent);
    }
}