package com.example.newsapps.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.newsapps.Activity.MainActivity;
import com.example.newsapps.R;

public class LoginForm extends AppCompatActivity {
    private  Button register, login;
    private String API = "http://192.168.6.191:8000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_loginform);
        register = findViewById(R.id.bt_register);
        login = findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(LoginForm.this, MainActivity.class);
                startActivity(start);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForm.this, RegisterForm.class);
                startActivity(intent);
            }
        });
    }
}