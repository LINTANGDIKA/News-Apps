package com.example.newsapps.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.newsapps.Activity.MainActivity;
import com.example.newsapps.R;

public class LoginForm extends AppCompatActivity {
    private TextView textView;
    private Dialog dialog;
    private Button register, login;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);
        dialog = new Dialog(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_login);
        register = findViewById(R.id.bt_register);
        login = findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast();
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

    private void toast() {
        dialog.setContentView(R.layout.dialog_center);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textView = dialog.findViewById(R.id.txtyes);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });

        dialog.show();

    }
}