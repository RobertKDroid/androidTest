package com.example.stacjonarny.testapp;

import android.os.AsyncTask;

import com.example.stacjonarny.testapp.Services.Weather;
import com.example.stacjonarny.testapp.Services.WeatherData;

/**
 * Created by Stacjonarny on 2016-10-01.
 */

public class AsyncTaskGetWeather extends AsyncTask<String,Void,WeatherData> {
    @Override
    protected WeatherData doInBackground(String[] params) {
        //params 0-city 1-country
        Weather.getInstance().getWeatherData(params[0],params[1]);
        WeatherData data = Weather.getInstance().getTodayWeather();
        return data;
    }

    @Override
    protected void onPostExecute(WeatherData data) {
        super.onPostExecute(data);

    }
}
