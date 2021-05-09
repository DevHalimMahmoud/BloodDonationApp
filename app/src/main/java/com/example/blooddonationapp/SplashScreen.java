package com.example.blooddonationapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.blooddonationapp.ui.login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar1);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                if(currentUser != null){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();}
            }
        }, 1000);
    }


}