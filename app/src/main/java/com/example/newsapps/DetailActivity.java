package com.example.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailActivity extends AppCompatActivity {
    private ImageView back, img, star_detail;
    private TextView title, description, source, date;
    private String  image, judul , deskripsi, sumber, tanggal;
    private Integer id;
    private Bundle bundle;
    Realm realm;
    RealmHelper realmHelper;
    ModelNews modelNews;
    private List<ModelNews> modelNewsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bundle();
        navigation();
    }

    private void bundle() {
        img = findViewById(R.id.im_detail);
        title = findViewById(R.id.judul_detail);
        description = findViewById(R.id.dc_detail);
        source = findViewById(R.id.source);
        date = findViewById(R.id.date_detail);
        star_detail = findViewById(R.id.star_detail);
        bundle = getIntent().getExtras();
        Realm.init(DetailActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        if (bundle  != null){
            judul = bundle.getString("title");
            deskripsi = bundle.getString("description");
            sumber = bundle.getString("source");
            tanggal = bundle.getString("date");
            image = bundle.getString("image");
            id = bundle.getInt("id");
            realm = Realm.getInstance(configuration);
            title.setText(judul);
            description.setText(deskripsi);
            source.setText(sumber);
            date.setText(tanggal);
            Picasso.get()
                    .load(image)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(img);
            star_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realmHelper = new RealmHelper(realm);
                    realmHelper.save(new ModelNews(judul,deskripsi,tanggal,sumber, image));
                    Log.d("a","a" + realm);
                    Toast.makeText(DetailActivity.this, "Berhasil Disimpan di Favorit :)", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void navigation() {
        back = findViewById(R.id.back_tohome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}