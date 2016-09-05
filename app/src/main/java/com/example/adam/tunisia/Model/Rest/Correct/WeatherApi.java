package com.example.adam.tunisia.Model.Rest.Correct;

import com.example.adam.tunisia.Model.Entities.OpenWeather.Model;
import com.example.adam.tunisia.Model.Rest.Correct.RestEx;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.adam.tunisia.Model.Rest.Correct.RestEx.WeatherKey;
import static com.example.adam.tunisia.Model.Rest.Correct.RestEx.WeatherRoot;

public interface WeatherApi {

    @GET(WeatherRoot + "/data/2.5/weather?appid=" + WeatherKey + "&units=metric")
    Call<Model> getWheatherReport(@Query("lat") String LATITUDE, @Query("lon") String LONGITUDE);
}