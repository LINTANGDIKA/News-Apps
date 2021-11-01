package com.example.newsapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.newsapps.Adapter.FavoriteAdapter;
import com.example.newsapps.Model.ModelNews;
import com.example.newsapps.R;
import com.example.newsapps.Ui.RealmHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ImageView search;
    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    List<ModelNews> modelNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = findViewById(R.id.recycleview_favorite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        modelNews = new ArrayList<>();

        modelNews = realmHelper.getAllNews();

        show();
        navigation();
    }
    private  void navigation(){
        bottomNavigationView = (bottomNavigationView) = findViewById(R.id.bottom_navigation_favorite);
        bottomNavigationView.setSelectedItemId(R.id.Favorite);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        search = findViewById(R.id.im_search_header_favorite);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mulai = new Intent(FavoriteActivity.this, SearchActivity.class);
                startActivity(mulai);
                overridePendingTransition(R.anim.fade_out, R.anim.fade);
            }
        });
    }
    protected void onRestart() {
        super.onRestart();
        favoriteAdapter.notifyDataSetChanged();
        show();
    }

    public void show(){
        favoriteAdapter = new FavoriteAdapter(getApplicationContext(), modelNews, new FavoriteAdapter.Callback() {
            @Override
            public void onClick(int position) {
              ModelNews model =  modelNews.get(position);
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
        recyclerView.setAdapter(favoriteAdapter);
    }
}