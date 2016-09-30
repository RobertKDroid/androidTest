package com.example.stacjonarny.testapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Stacjonarny on 2016-09-29.
 */

public class AsyncTaskGetArticles extends AsyncTask<String,Integer,Document> {  //params progress result

    Document document;

    public AsyncTaskGetArticles(Document document){
        this.document = document;
    }


    private void callBackDocument(Document document){
        this.document = document;
        Log.d("ASYNCTASK","Document called");

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected Document doInBackground(String... param) { //returns document
        Log.d("ASYNCTASK","RUNNING");
        String url = param[0];
        Document d = null;
        try {
            d = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        if(document!=null){
            Log.d("ASYNCTASK","NOT NULL");
        }else{
            Log.d("ASYNCTASK","NULL");
        }
        callBackDocument(document);
       // System.out.println(document);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {   //progress update value
        super.onProgressUpdate(values);
    }
}
