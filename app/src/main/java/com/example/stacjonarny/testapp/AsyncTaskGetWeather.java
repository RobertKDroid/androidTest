package com.example.stacjonarny.testapp;

import android.os.AsyncTask;

import com.example.stacjonarny.testapp.Services.Weather;
import com.google.gson.Gson;

/**
 * Created by Stacjonarny on 2016-10-01.
 */

public class AsyncTaskGetWeather extends AsyncTask<String,Void,Weather> {
    @Override
    protected Weather doInBackground(String[] params) {
        Weather weather = new Weather();
        Weather w = weather.getWeather("asd");
        return w;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);

    }
}
