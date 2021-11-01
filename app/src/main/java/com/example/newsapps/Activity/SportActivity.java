package com.example.newsapps.Activity;

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
import com.example.newsapps.Adapter.MainAdapter;
import com.example.newsapps.Model.ModelNews;
import com.example.newsapps.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SportActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView, navigationup;
    private ImageView search;
    private ArrayList<ModelNews> modelsport;
    private String judul, gambar, description, source, date;
    private RecyclerView recyclerView;
    private MainAdapter sport;
    private String Sport = "sports";
    private String API = "https://newsapi.org/v2/top-headlines";
    private String Api_key = "70c9c73cd4764e449efbf91d4fd3a065";
    private String Negara = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        recyclerView = findViewById(R.id.recycleview_sport);
        getData();
        navigation();
    }

    private void getData() {
        modelsport = new ArrayList<>();
        AndroidNetworking.get(API)
                .addQueryParameter("country", Negara )
                .addQueryParameter("category", Sport )
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
                                description = resultObj.getString( "description");
                                source = resultObj.getString("author");
                                date = resultObj.getString( "publishedAt");
                                modelsport.add(new ModelNews(i,  judul,description,date,source, gambar));
                            }
                            sport = new MainAdapter(SportActivity.this, modelsport, new MainAdapter.Callback() {
                                @Override
                                public void Call(int position) {
                                    ModelNews model = modelsport.get(position);
                                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                    intent.putExtra("title", model.getJudul());
                                    intent.putExtra("id", model.getId());
                                    intent.putExtra("description", model.getDescription());
                                    intent.putExtra("date", model.getDate());
                                    intent.putExtra("source", model.getSource());
                                    intent.putExtra("image", model.getImage());
                                    startActivity(intent);
                                }
                            });
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SportActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(sport);
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
        navigationup = findViewById(R.id.navigation_up_sport);
        navigationup.setSelectedItemId(R.id.Sport);
        navigationup.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Berita_utama:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        bottomNavigationView = (bottomNavigationView) = findViewById(R.id.bottom_navigation_sport);
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
        search = findViewById(R.id.im_search_header_sport);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mulai = new Intent(SportActivity.this, SearchActivity.class);
                startActivity(mulai);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });
    }
}