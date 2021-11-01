package com.example.newsapps.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.newsapps.Adapter.MainAdapter;
import com.example.newsapps.Model.ModelNews;
import com.example.newsapps.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class SearchActivity extends AppCompatActivity {
    private String API = "https://newsapi.org/v2/top-headlines";
    private String Negara = "id";
    private String Api_key = "70c9c73cd4764e449efbf91d4fd3a065";
    private ImageView imageView;
    private SearchView searchView;
    private RecyclerView recyclerView;
    String judul, gambar, description, source, date;
    MainAdapter mainAdapter;
    List<ModelNews> modelNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.sc_recycleview);
        recyclerView.setHasFixedSize(true);
        setRecycleview();
        setnavigation();
    }

    private void setRecycleview() {
        modelNews = new ArrayList<>();
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
                                modelNews.add(new ModelNews(i, judul,description,date,source, gambar));
                            }
                            mainAdapter = new MainAdapter(SearchActivity.this, modelNews, new MainAdapter.Callback() {
                                @Override
                                public void Call(int position) {
                                    ModelNews model = modelNews.get(position);
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
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mainAdapter);
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

    private void setnavigation() {
        imageView = findViewById(R.id.back_sc);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    mainAdapter.getFilter().filter(s);
                } catch (Exception e) {
                    Log.d("error", ""+e.toString());
                }
                return false;
            }
        });
    }
}