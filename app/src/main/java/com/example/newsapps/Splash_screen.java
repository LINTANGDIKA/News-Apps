package com.example.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.icu.number.Scale;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.zooming_entrances.ZoomInAnimator;

public class Splash_screen extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView, String.valueOf(imageView.getImageAlpha()), 0f);
                objectAnimator1.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimator1);
                animatorSet.start();
                Intent Start =new Intent(Splash_screen.this, Splash_screen2.class);
                startActivity(Start);
                finish();

            }
        }, 1000);
    }
}