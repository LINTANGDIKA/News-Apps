package com.example.newsapps.Ui;

import android.util.Log;
import android.view.Display;

import com.example.newsapps.Model.ModelNews;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    List<ModelNews> storeList;
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
//public void delete(ModelNews modelNews){
//    final RealmResults<ModelNews> model = realm.where(ModelNews.class).equalTo("description", modelNews.getDescription()).findAll();
//    realm.executeTransaction(new Realm.Transaction() {
//        @Override
//        public void execute(Realm realm) {
//            model.deleteAllFromRealm();
//            final RealmResults<ModelNews> allItems = realm.where(ModelNews.class).findAll();
////            storeList = realm.copyFromRealm(allItems);;
////            Collections.sort(storeList);
//        }
//    });
//}
}
