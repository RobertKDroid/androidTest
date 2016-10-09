package com.example.stacjonarny.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stacjonarny.testapp.Services.WeatherForecast;

/**
 * Created by Stacjonarny on 2016-10-09.
 */

public class ForecastFragment extends Fragment {
    TextView mTextViewDate;
    TextView mTextViewState;
    TextView mTextViewDay;
    WeatherForecast wf;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_forecast,container,false);
        mTextViewDate =(TextView) v.findViewById(R.id.date);
        mTextViewState =(TextView) v.findViewById(R.id.state);
        mTextViewDay =(TextView) v.findViewById(R.id.day);
        wf = new WeatherForecast();
        wf.setState(getArguments().getString("state"));
        wf.setDate(getArguments().getString("date"));
        wf.setDay(getArguments().getString("day"));
        setupData();
        return v;
    }


    private void setupData(){
        mTextViewDate.setText(wf.getDate());
        mTextViewDay.setText(wf.getDay());
        mTextViewState.setText(wf.getState());
    }
}
