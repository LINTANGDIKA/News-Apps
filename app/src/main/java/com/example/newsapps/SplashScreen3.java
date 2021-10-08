package com.example.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen3 extends AppCompatActivity {
//    private AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen3);
//        avi = findViewById(R.id.avi);
//        avi.setIndicator("BallClipRotateMultipleIndicator");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Start =new Intent(SplashScreen3.this, LoginForm.class);
                startActivity(Start);
                finish();
            }
        }, 2500);
    }
}