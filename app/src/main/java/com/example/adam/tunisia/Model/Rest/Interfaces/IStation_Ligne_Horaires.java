package com.example.adam.tunisia.Model.Rest.Interfaces;

import com.example.adam.tunisia.Model.Entities.Station_Ligne;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IStation_Ligne_Horaires {

    @GET("rest/station_ligne/crud")
    Call<List<Station_Ligne>> getStation_Lignes_Horaires();

}
