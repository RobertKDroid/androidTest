package com.example.stacjonarny.testapp.Services;

/**
 * Created by Stacjonarny on 2016-10-01.
 */

public class WeatherData {
    private String region, city, sunset, sunrise;
    private float windSpeed, windDirection, windChill,temperature,
            atmospherePressure, atmosphereVisibility, atmosphereRising, atmosphereHumidity;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    public float getWindChill() {
        return windChill;
    }

    public void setWindChill(float windChill) {
        this.windChill = windChill;
    }

    public float getAtmospherePressure() {
        return atmospherePressure;
    }

    public void setAtmospherePressure(float atmospherePressure) {
        this.atmospherePressure = atmospherePressure;
    }

    public float getAtmosphereVisibility() {
        return atmosphereVisibility;
    }

    public void setAtmosphereVisibility(float atmosphereVisibility) {
        this.atmosphereVisibility = atmosphereVisibility;
    }

    public float getAtmosphereRising() {
        return atmosphereRising;
    }

    public void setAtmosphereRising(float atmosphereRising) {
        this.atmosphereRising = atmosphereRising;
    }

    public float getAtmosphereHumidity() {
        return atmosphereHumidity;
    }

    public void setAtmosphereHumidity(float atmosphereHumidity) {
        this.atmosphereHumidity = atmosphereHumidity;
    }
}
