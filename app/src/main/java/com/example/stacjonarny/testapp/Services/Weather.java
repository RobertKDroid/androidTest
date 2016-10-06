package com.example.stacjonarny.testapp.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Stacjonarny on 2016-10-01.
 */

public class Weather {

    private static final String END_POINT="https://query.yahooapis.com/v1/public/yql?q=";
    private static Weather instance = null;
    private JSONObject forecastData=null;

    protected Weather(){}

    public static Weather getInstance(){
        if(instance== null){
            instance = new Weather();
        }
        return instance;
    }

    /*
            "description":"Yahoo! Weather for Chrzanow, Lesser Poland, PL",
            "language":"en-us",
            "lastBuildDate":"Sat, 01 Oct 2016 04:48 PM CEST",
            "ttl":"60",
            "location":{  },
            "wind":{  },
            "atmosphere":{  },
            "astronomy":{  },
            "image":{  },
            "item":{  } forecast for 7 days
 */


    public void getWeatherData(String city, String country){
        URL url = null;

        String place = "Chrzanów";
        String query = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\""+city+","+country+"\") and u=\"c\"";
        URLConnection urlConnection = null;
        try {
            url = new URL(END_POINT+URLEncoder.encode(query,"UTF-8")+"&format=json");
             urlConnection = url.openConnection();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                this.forecastData = new JSONObject(connectionRead(urlConnection));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        getTodayWeather();
    }


    public WeatherData getTodayWeather() {
        WeatherData data = new WeatherData();
        JSONArray todayForecast = new JSONArray();

        try{
            todayForecast.put(getForecastByKey(this.forecastData,"location"));
            todayForecast.put(getForecastByKey(this.forecastData,"wind"));
            todayForecast.put(getForecastByKey(this.forecastData,"atmosphere"));
            todayForecast.put(getForecastByKey(this.forecastData,"astronomy"));
            todayForecast.put(getForecastByKey(this.forecastData,"condition"));

            data.setRegion(todayForecast.getJSONObject(0).getString("region"));
            data.setCity(todayForecast.getJSONObject(0).getString("city"));
            data.setWindSpeed(todayForecast.getJSONObject(1).getLong("speed"));
            data.setWindDirection(todayForecast.getJSONObject(1).getLong("direction"));
            data.setWindChill(todayForecast.getJSONObject(1).getLong("chill"));
            data.setAtmospherePressure(todayForecast.getJSONObject(2).getLong("pressure"));
            data.setAtmosphereVisibility(todayForecast.getJSONObject(2).getLong("visibility"));
            data.setAtmosphereHumidity(todayForecast.getJSONObject(2).getLong("humidity"));
            data.setSunrise(todayForecast.getJSONObject(3).getString("sunrise"));
            data.setSunset(todayForecast.getJSONObject(3).getString("sunset"));
            data.setTemperature(todayForecast.getJSONObject(4).getLong("temp"));

            JSONArray futureForecast = this.forecastData.getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONArray("forecast");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    private JSONObject getForecastByKey(JSONObject response,String key) {


        try {
            if(key.equals("condition")){
                response = response.getJSONObject("query")
                        .getJSONObject("results")
                        .getJSONObject("channel")
                        .getJSONObject("item")
                        .getJSONObject(key);
            }else {
                response = response.getJSONObject("query")
                        .getJSONObject("results")
                        .getJSONObject("channel")
                        .getJSONObject(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
        return response;
    }

    private String connectionRead(URLConnection connection) {
        String input = "";
        String output = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((input = bufferedReader.readLine()) != null) {
                input += input;
                output += input;
                System.out.println("READING INPUT ");
                System.out.println("INPUT = " + input);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }


}
