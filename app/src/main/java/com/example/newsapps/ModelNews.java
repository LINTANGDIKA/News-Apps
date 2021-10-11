package com.example.newsapps;

public class ModelNews {
    private int id;
    private String Judul;
    private String description;
    private String date;
    private String source;

    public ModelNews(int id, String Judul, String description, String date, String source, String image) {
        this.id = id;
        this.Judul = Judul;
        this.description = description;
        this.date = date;
        this.source = source;
        this.image = image;
    }



    public int getId() {
        return id;
    }

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
