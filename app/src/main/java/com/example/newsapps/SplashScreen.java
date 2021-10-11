package com.example.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.logo);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Intent Start =new Intent(SplashScreen.this, SplashScreen2.class);
                startActivity(Start);
                overridePendingTransition(0, 0);
                finish();

            }
        }, 1000);
    }
}