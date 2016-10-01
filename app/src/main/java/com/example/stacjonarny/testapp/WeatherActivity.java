package com.example.stacjonarny.testapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stacjonarny.testapp.Services.Weather;
import com.example.stacjonarny.testapp.Services.WeatherData;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "ASYNCTASKGETWEATHER";
    EditText mEditTextCity, mEditTextCountry;
    TextView mTextViewTemperature, mTextViewSunrise,
            mTextViewSunset, mTextViewWind,
            mTextViewHumidity, mTextViewPressure;
    final WeatherData w = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupView();


        loadWeatherData(w, mEditTextCity.getText().toString(), mEditTextCountry.getText().toString());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing weather", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                loadWeatherData(w, mEditTextCity.getText().toString(), mEditTextCountry.getText().toString());

            }
        });
    }

    private void setupView() {
        TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    loadWeatherData(w, mEditTextCity.getText().toString()
                            , mEditTextCountry.getText().toString());
                }
                return true;
            }
        };


        mEditTextCity = (EditText) findViewById(R.id.edittext_city);
        mEditTextCity.setOnEditorActionListener(editorActionListener);
        mEditTextCountry = (EditText) findViewById(R.id.edittext_country);
        mEditTextCountry.setOnEditorActionListener(editorActionListener);
        mEditTextCity.setText("Kraków");
        mEditTextCountry.setText("PL");

        mTextViewHumidity = (TextView) findViewById(R.id.textview_weather_humidity);
        mTextViewPressure = (TextView) findViewById(R.id.textview_weather_pressure);
        mTextViewSunrise = (TextView) findViewById(R.id.textview_weather_sunrise);
        mTextViewSunset = (TextView) findViewById(R.id.textview_weather_sunset);
        mTextViewTemperature = (TextView) findViewById(R.id.textview_weather_temperature);
        mTextViewWind = (TextView) findViewById(R.id.textview_weather_wind_speed_direction);
    }

    private void loadWeatherData(WeatherData w, String city, String country) {

        try {
            w = new AsyncTaskGetWeather().execute(city, country).get();
            setupDataIntoView(w);
            Log.d(TAG, "onCreate: WEATHER GOT");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setupDataIntoView(WeatherData w) {
        mTextViewHumidity.setText(w.getAtmosphereHumidity() + " %");
        mTextViewPressure.setText(w.getAtmospherePressure() + " mBar LOL");
        mTextViewWind.setText(w.getWindSpeed() + " Km/h Direction " + w.getWindDirection() + " °");
        mTextViewSunrise.setText(w.getSunrise());
        mTextViewSunset.setText(w.getSunset());
        mTextViewTemperature.setText(w.getTemperature() + " °C");
    }

}
