package com.example.stacjonarny.testapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.stacjonarny.testapp.Services.Weather;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG= "ASYNCTASKGETWEATHER";
    TextView mTextViewWeatehr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewWeatehr=(TextView)findViewById(R.id.weather_text);

        Weather w = null;
        try {
           w = new AsyncTaskGetWeather().execute().get();

            Log.d(TAG, "onCreate: WEATHER GOT");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
