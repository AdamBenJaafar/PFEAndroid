package com.example.adam.tunisia.Model.Rest.Interfaces;

import com.example.adam.tunisia.Model.Entities.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IStation {

    @GET("rest/stations/crud")
    Call<List<Station>> getStations();

}
