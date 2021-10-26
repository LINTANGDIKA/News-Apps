package com.example.newsapps;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;
    public RealmHelper(Realm realm) { this.realm = realm; }

    public void save (ModelNews modelNews){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Number currentIdNum = realm.where(ModelNews.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1; }
                    modelNews.setId(nextId);
                    ModelNews modelNews1 = realm.copyToRealm(modelNews);
                    Log.e("Created", "Database was created" + modelNews1);
                }
            }
        });
    }
    public List<ModelNews> getAllNews(){
        RealmResults<ModelNews> results = realm.where(ModelNews.class).findAll();
        return results;
    }
    public void delete(Integer id){
        final RealmResults<ModelNews> modelNews = realm.where(ModelNews.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                modelNews.deleteFromRealm(0);
            }
        });
    }
//    public ModelNews getIdNews(Integer id){
//        ModelNews results = realm.where(ModelNews.class).equalTo("id",id).findFirst();
//        return results;
//    }
}
