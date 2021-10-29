package com.example.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginForm extends AppCompatActivity {
    private  Button register, login;
    private EditText ed_email, ed_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);
        register = findViewById(R.id.bt_register);
        login = findViewById(R.id.bt_login);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ed_email.getText().toString();
                String password = ed_password.getText().toString();
                if(email.trim().isEmpty() && password.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Tolong Di isi Formnya!", Toast.LENGTH_SHORT).show();
                } else {
                    login(email , password);
                }

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
    public void login(String email, String password){
        AndroidNetworking.post("http://192.168.1.8:8000/login")
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("messages");
                            if(status.contains("Success")) {
                                Toast.makeText(LoginForm.this, message + " :) ", Toast.LENGTH_SHORT).show();
                                Intent start = new Intent(LoginForm.this, MainActivity.class);
                                startActivity(start);
                                overridePendingTransition(R.anim.atas, R.anim.fade);
                            } else if(status.contains("Gagal")){
                                Toast.makeText(LoginForm.this, message + " :( ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("","" + error.getErrorDetail());
                        Log.d(""," "+ error.getMessage());
                        Log.d(""," "+ error.getErrorCode());
                        Log.d(""," "+ error.getResponse());
                    }
                });

    }
}