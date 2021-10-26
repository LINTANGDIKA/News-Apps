package com.example.newsapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String API = "https://newsapi.org/v2/top-headlines";
    private String Negara = "id";
    private String Api_key = "70c9c73cd4764e449efbf91d4fd3a065";
    private BottomNavigationView bottomNavigationView, navigationup;
    private ImageView search;
    RecyclerView recyclerView;
    private List<ModelNews> beritautama;
    MainAdapter main;
    String judul, gambar, description, source, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleview_main);
        recyclerView.setHasFixedSize(true);
        getdata();
        navigation();


    }
    private  void navigation(){
        navigationup = findViewById(R.id.navigation_up);
        navigationup.setSelectedItemId(R.id.Berita_utama);
        navigationup.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Sport:
                        startActivity(new Intent(getApplicationContext(), SportActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Health:
                        startActivity(new Intent(getApplicationContext(), HealthActivity.class));
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
        bottomNavigationView = (bottomNavigationView) = findViewById(R.id.bottom_navigation);
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
        search = findViewById(R.id.im_search_header_m);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mulai = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(mulai);
                overridePendingTransition(R.anim.fade_out, R.anim.fade);
            }
        });
    }
    private  void getdata() {
        beritautama = new ArrayList<>();
        AndroidNetworking.get(API)
                .addQueryParameter("country", Negara )
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
                                source = resultObj.getString("author");
                                date = resultObj.getString( "publishedAt");
                                beritautama.add(new ModelNews(judul,description,date,source, gambar));
                            }
                            main = new MainAdapter(MainActivity.this, beritautama, new MainAdapter.Callback() {
                                @Override
                                public void Call(int position) {
                                     ModelNews model = beritautama.get(position);
                                     Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                     intent.putExtra("title", model.getJudul());
                                     intent.putExtra("description", model.getDescription());
                                     intent.putExtra("date", model.getDate());
                                     intent.putExtra("source", model.getSource());
                                     intent.putExtra("image", model.getImage());
                                     intent.putExtra("id", model.getId());
                                     startActivity(intent);

                                }
                            });

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(main);
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

}