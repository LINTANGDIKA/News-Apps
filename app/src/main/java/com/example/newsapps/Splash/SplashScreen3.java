package com.example.newsapps.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.newsapps.Activity.MainActivity;
import com.example.newsapps.R;
import com.example.newsapps.Ui.LoginForm;

public class SplashScreen3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Start =new Intent(SplashScreen3.this,  LoginForm.class);
                startActivity(Start);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
                finish();
            }
        }, 2500);
    }
}