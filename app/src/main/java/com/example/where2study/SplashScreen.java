package com.example.where2study;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends Activity {

    private final String TAG = "SPLASHSCREEN";
    private static final int SPLASH_DISPLAY_LENGTH = 1500;
    private FirebaseAuth auth;
    private String name, id, email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "In onCreate");
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_splash_screen);
            if (auth.getCurrentUser() == null) {
                //Start the HomeScreen activity if the user is not logged in
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreen.this, Login.class));
                        finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            } else {
                //Start the main activity  if the user is logged in
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseUser currentUser = auth.getCurrentUser();
                        email = currentUser.getEmail();
                        id = currentUser.getUid();
                        name = currentUser.getDisplayName();
                        Log.d(TAG, "Current User: " + name);
                        Log.d(TAG, email);
                        Log.d(TAG, id);
                        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("username", name);
                        editor.putString("email", email);
                        editor.putString("userid", id);
                        editor.commit();
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Starting the SplashScreen activity");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "SplashScreen activity lifecycle has completed");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause method in execution");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume method in effect");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SplashScreen activity has been destroyed");
    }
}
