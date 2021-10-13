package com.example.newsapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ImageView back, img;
    private TextView title, description, source, date;
    private String  image, judul , deskripsi, sumber, tanggal;
    private Bundle bundle;
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
        bundle = getIntent().getExtras();

        if (bundle  != null){
            judul = bundle.getString("title");
            deskripsi = bundle.getString("description");
            sumber = bundle.getString("source");
            tanggal = bundle.getString("date");
            image = bundle.getString("image");

            title.setText(judul);
            description.setText(deskripsi);
            source.setText(sumber);
            date.setText(tanggal);
            Picasso.get()
                    .load(image)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(img);
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