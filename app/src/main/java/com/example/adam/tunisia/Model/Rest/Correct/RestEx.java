package com.example.adam.tunisia.Model.Rest.Correct;

/**
 * Created by adam- on 06/08/2016.
 */
import android.content.Context;

import com.example.adam.tunisia.Model.Rest.GooglePlaces.GooglePlacesAPI;

import com.example.adam.tunisia.Model.Rest.OpenWeather.OpenWeatherAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestEx {

    public static final String GoogleRoot = "https://maps.googleapis.com/";
    public static final String WeatherRoot = "http://api.openweathermap.org/";
    public static final String TunisiaRoot = "http://projet-pfe16.rhcloud.com/";

    public static final String GoogleKey = "AIzaSyBze1aC3aLS4CblBPTUeMLZHOXa5d27ydQ";
    public static final String WeatherKey = "9402931e89ea7125c6379a7a88afc32f";

    private static boolean initialized = false;
    private static OpenWeatherAPI openWeatherAPI;
    private static GooglePlacesAPI googlePlacesAPI;
    private static TunisiaApi tunisiaApi;


    public static void init(Context context) {
        // your choice you can create retrofit instance for each of your api, or
        // you could simply use full url for google and open weather calls :)

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TunisiaRoot)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeatherAPI = retrofit.create(OpenWeatherAPI.class);
        googlePlacesAPI = retrofit.create(GooglePlacesAPI.class);
        tunisiaApi = retrofit.create(TunisiaApi.class);

        initialized = true;
    }

    public static OpenWeatherAPI getOpenWeatherAPI() {
        checkInitialized();
        return openWeatherAPI;
    }

    public static GooglePlacesAPI getGooglePlacesAPI() {
        checkInitialized();
        return googlePlacesAPI;
    }

    public static TunisiaApi getTunisiaApi() {
        checkInitialized();
        return tunisiaApi;
    }

    private static void checkInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Init not called in on create");
        }
    }
}