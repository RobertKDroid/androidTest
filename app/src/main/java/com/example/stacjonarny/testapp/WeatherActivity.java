package com.example.stacjonarny.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stacjonarny.testapp.Services.WeatherData;
import com.example.stacjonarny.testapp.Services.WeatherForecast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "ASYNCTASKGETWEATHER";
    EditText mEditTextCity, mEditTextCountry;
    TextView mTextViewTemperature, mTextViewSunrise,
            mTextViewSunset, mTextViewWind,
            mTextViewHumidity, mTextViewPressure,mTextViewCityCountry;
    ListView mDrawerList;
    LinearLayout mLinearLayoutForecasts;
    ArrayList<ForecastFragment> forecasts;
    DrawerLayout mDrawerLayout;
    static DrawerLayout dl;
    private String drawerAdapterItems[] = {"Gallery","Articles","Login","Logout"};


    final WeatherData w = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        forecasts = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        mLinearLayoutForecasts = (LinearLayout) findViewById(R.id.linearlayout_forecasts);


        ImageView mNavHeaderImage = new ImageView(this);
        mNavHeaderImage.setImageResource(R.drawable.dixi_1);
        mNavHeaderImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setupView();
        createDrawerWithHeaderImage(mNavHeaderImage,240,60);


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

    @Override
    protected void onResume() {
        super.onResume();
        loadWeatherData(w, mEditTextCity.getText().toString(), mEditTextCountry.getText().toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    private static Intent makeActivityIntent(Context c, Class activityClass){
        Intent i = new Intent(c,activityClass);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return i;
    }
    private int convertDptoPX(Context c, float dp){
        Resources r = c.getResources();
        dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());


        return Math.round(dp);
    }
    private void createDrawerWithHeaderImage(ImageView mNavHeaderImage, int DPx, int DPy) {
        mNavHeaderImage.setLayoutParams(new AbsListView.LayoutParams(convertDptoPX(this,DPx),convertDptoPX(this,DPy)));
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.addHeaderView(mNavHeaderImage);
        ArrayAdapter drawerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,drawerAdapterItems);
        mDrawerList.setAdapter(drawerAdapter);
        setDrawerOnItemClickListener(mDrawerList,this);
    }
    private static void setDrawerOnItemClickListener(ListView list, final Activity a){

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = null;

                switch(position){
                    case 1:
                        dl.closeDrawers();
                        i=makeActivityIntent(a.getApplicationContext(),VideoGalleryActivity.class);
                        a.startActivity(i);
                        break;
                    default:
                        Log.d(TAG, "DEFAULT NO ACTION DESIGNED");

                }
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

        mTextViewCityCountry = (TextView) findViewById(R.id.textview_actual_city);
        mTextViewHumidity = (TextView) findViewById(R.id.textview_weather_humidity);
        mTextViewPressure = (TextView) findViewById(R.id.textview_weather_pressure);
        mTextViewSunrise = (TextView) findViewById(R.id.textview_weather_sunrise);
        mTextViewSunset = (TextView) findViewById(R.id.textview_weather_sunset);
        mTextViewTemperature = (TextView) findViewById(R.id.textview_weather_temperature);
        mTextViewWind = (TextView) findViewById(R.id.textview_weather_wind_speed_direction);
    }

    private void loadWeatherData(WeatherData w, String city, String country) {
        if(isOnline()) {
            try {
                w = new AsyncTaskGetWeather().execute(city, country).get();
                Collections.sort(w.getForecast(), new Comparator<WeatherForecast>() {
                    @Override
                    public int compare(WeatherForecast o1, WeatherForecast o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }finally {
                if(w!=null) {
                    setupDataIntoView(w);
                    setupForecastFragments(w);
                }
            }
        }else {
            Toast.makeText(this, "Check net Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void setupForecastFragments(WeatherData w){
        FragmentManager fm = getSupportFragmentManager();
        if(!forecasts.isEmpty()){
            FragmentTransaction ft = fm.beginTransaction();
            for(ForecastFragment f:forecasts){
                ft.remove(f);
            }
            ft.commit();
            forecasts.clear();
        }
        FragmentTransaction ft = fm.beginTransaction();
        for(int i=0;i<7;i++){
            WeatherForecast wf = w.getForecast().get(i);
            ForecastFragment f = new ForecastFragment();
            Bundle b = new Bundle();
            b.putString("date", wf.getDate());
            b.putString("day", wf.getDay());
            b.putString("state", wf.getState());
            f.setArguments(b);
            forecasts.add(f);
            ft.add(mLinearLayoutForecasts.getId(),f,"forecast");
            b=null;
            f=null;
            wf=null;
        }
        ft.commit();
    }
    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo !=null && netInfo.isConnectedOrConnecting();
    }
    private void setupDataIntoView(WeatherData w) {
        mTextViewCityCountry.setText(w.getCity()+ " " + w.getRegion());
        mTextViewHumidity.setText(w.getAtmosphereHumidity() + " %");
        mTextViewPressure.setText(w.getAtmospherePressure() + " mBar LOL");
        mTextViewWind.setText(w.getWindSpeed() + " Km/h Direction " + w.getWindDirection() + " °");
        mTextViewSunrise.setText(w.getSunrise());
        mTextViewSunset.setText(w.getSunset());
        mTextViewTemperature.setText(w.getTemperature() + " °C");

    }

}
