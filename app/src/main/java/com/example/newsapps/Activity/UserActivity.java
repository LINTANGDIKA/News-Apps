package com.example.newsapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.newsapps.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember","false");
                editor.apply();
                finish();
            }
        });
        bottomNavigationView = (bottomNavigationView) = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.User);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Favorite:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Trnding:
                        startActivity(new Intent(getApplicationContext(), TrendingActivity.class));
                        overridePendingTransition(0, 0);
                        return  true;
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }
}