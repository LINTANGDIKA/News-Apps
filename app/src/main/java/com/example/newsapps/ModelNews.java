package com.example.newsapps;

import android.content.Intent;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ModelNews extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String Judul;
    private String description;
    private String date;
    private String source;
    public ModelNews(){}
    public ModelNews(String Judul, String description, String date, String source, String image) {
        this.Judul = Judul;
        this.description = description;
        this.date = date;
        this.source = source;
        this.image = image;
    }
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;



}
