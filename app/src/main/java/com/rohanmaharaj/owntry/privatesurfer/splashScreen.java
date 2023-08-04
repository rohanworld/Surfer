package com.rohanmaharaj.owntry.privatesurfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Handler hndlr = new Handler();
        hndlr.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splashScreen.this, MainActivity.class));
            }
        }, 2468);
    }
}