package com.example.newsapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HealthActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView, navigationup;
    private ImageView search;
    private ArrayList<ModelNews> modelhealth;
    private String judul, gambar, description, source, date;
    private RecyclerView recyclerView;
    private MainAdapter health;
    private String Health= "health";
    private String API = "https://newsapi.org/v2/top-headlines";
    private String Api_key = "70c9c73cd4764e449efbf91d4fd3a065";
    private String Negara = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        recyclerView = findViewById(R.id.recycleview_health);
        getData();
        navigation();
    }
    private void getData() {
        modelhealth = new ArrayList<>();
        AndroidNetworking.get(API)
                .addQueryParameter("country", Negara )
                .addQueryParameter("category", Health )
                .addQueryParameter("apiKey", Api_key )
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response: ", "yes");
                        try {
                            JSONArray resultArray = response.getJSONArray( "articles");
                            for (int i = 0; i < resultArray.length(); i++) {
                                JSONObject resultObj = resultArray.getJSONObject(i);
                                judul = resultObj.getString( "title");
                                gambar = resultObj.getString( "urlToImage");
                                description = resultObj.getString( "content");
                                source = resultObj.getString("source");
                                date = resultObj.getString( "publishedAt");
                                modelhealth.add(new ModelNews(i, judul,description,date,source, gambar));
                            }
                            health = new MainAdapter(HealthActivity.this, modelhealth);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HealthActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(health);
                        } catch (Exception e) {
                            Log.d("Error: ", e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Something error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private  void navigation(){
        navigationup = findViewById(R.id.navigation_up_health);
        navigationup.setSelectedItemId(R.id.Health);
        navigationup.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Berita_utama:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Sport:
                        startActivity(new Intent(getApplicationContext(), SportActivity.class));
                        overridePendingTransition(0, 0);
                        return  true;
                    case R.id.Otomotif:
                        startActivity(new Intent(getApplicationContext(), OtomotifActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView = (bottomNavigationView) = findViewById(R.id.bottom_navigation_health);
        bottomNavigationView.setSelectedItemId(R.id.Home);
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
                    case R.id.User:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        search = findViewById(R.id.im_search_header_health);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mulai = new Intent(HealthActivity.this, SearchActivity.class);
                startActivity(mulai);
                overridePendingTransition(R.anim.fade_out, R.anim.fade);
            }
        });
    }
}