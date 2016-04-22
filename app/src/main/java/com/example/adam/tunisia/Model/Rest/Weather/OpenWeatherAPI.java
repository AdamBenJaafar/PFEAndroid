package com.example.adam.tunisia.Model.Rest.Weather;

import com.example.adam.tunisia.Model.Entities.OpenWeather.Model;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Adam on 31/03/2016.
 */
public interface OpenWeatherAPI {

    @GET("data/2.5/weather?q=London,uk&appid=9402931e89ea7125c6379a7a88afc32f")
    Call<Model> getWheatherReport();
}