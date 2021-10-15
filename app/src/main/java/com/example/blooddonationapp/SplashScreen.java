package com.example.blooddonationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Activitys.LoginActivity;
import com.example.blooddonationapp.Utils.FirebaseAuthSingleton;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    private final FirebaseAuthSingleton mAuth = FirebaseAuthSingleton.INSTANCE;
    private final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar1);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over\

                if (isFirstTime()) {
                    // What you do when the Application is Opened First time Goes here
                    Intent s = new Intent(getApplicationContext(), MyCustomAppIntro.class);
                    startActivity(s);
                    finish();
                } else if (currentUser == null) {
                    //check if no user is logged in
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 1000);
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.apply();
        }
        return !ranBefore;

    }
}