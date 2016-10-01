package com.example.stacjonarny.testapp.Services;

import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;

/**
 * Created by Stacjonarny on 2016-10-01.
 */

public class Weather {
    public float getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(float temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    private float temperatureCelsius;
    private float windSpeed;
    private String windDirection;

    public Weather getWeather(String query) {
        URL url = null;
        try {
            url = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20wind%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text=%27chicago,%20il%27)&format=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Weather weather = new Weather();
        String json;
        try {
            URLConnection urlConnection = url.openConnection();
            json = connectionRead(urlConnection);
            JSONObject jsonObject = null;

            jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONObject("query").getJSONObject("results").getJSONArray("channel");
            weather.setTemperatureCelsius(jsonObject.getJSONObject("query").getInt("count"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return weather;
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
