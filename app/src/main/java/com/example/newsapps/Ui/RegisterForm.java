package com.example.newsapps.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.newsapps.Activity.MainActivity;
import com.example.newsapps.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterForm extends AppCompatActivity {
    private  ImageView back;
    EditText ed_email, ed_username, ed_password, ed_nama, ed_no, ed_alamat, ed_umur;
    private Button bt_register2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);
        back = findViewById(R.id.bt_back);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_username = findViewById(R.id.ed_username);
        ed_nama = findViewById(R.id.ed_nama);
        ed_no = findViewById(R.id.ed_no);
        ed_alamat = findViewById(R.id.ed_alamat);
        ed_umur = findViewById(R.id.ed_umur);
        bt_register2 = findViewById(R.id.bt_register2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterForm.this, LoginForm.class);
                startActivity(intent);
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String email = ed_email.getText().toString();
        String password = ed_password.getText().toString();
        String username = ed_username.getText().toString();
        String nama = ed_nama.getText().toString();
        String noTelp = ed_no.getText().toString();
        String alamat = ed_alamat.getText().toString();
        String umur = ed_umur.getText().toString();
        String gender = spinner.getSelectedItem().toString();
        bt_register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.trim().isEmpty() && password.trim().isEmpty() && username.trim().isEmpty() && nama.trim().isEmpty() && noTelp.trim().isEmpty() && alamat.trim().isEmpty() && umur.trim().isEmpty() && gender.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Tolong Di isi Formnya! ", Toast.LENGTH_SHORT).show();
                }
                else {
//                    register(email, password, username, nama, noTelp, alamat, umur, gender);
                    Log.d("","data : " + email + password + username + nama + noTelp + alamat + umur + gender);
                }
            }
        });


    }
    public void register(String email, String password, String username, String nama, String notelp, String alamat, String umur, String gender) {
        AndroidNetworking.post("http://192.168.1.9:8000/register")
                .addBodyParameter("email", email)
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .addBodyParameter("full_name", nama)
                .addBodyParameter("phone_number", notelp)
                .addBodyParameter("country", alamat)
                .addBodyParameter("gender", gender)
                .addBodyParameter("age", umur)
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
                                Toast.makeText(RegisterForm.this, message + " :) ", Toast.LENGTH_SHORT).show();
                                Intent start = new Intent(RegisterForm.this, LoginForm.class);
                                startActivity(start);
                                overridePendingTransition(R.anim.fade, R.anim.fade);
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