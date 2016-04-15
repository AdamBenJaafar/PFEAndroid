package com.example.adam.tunisia.Model.Rest.Interfaces;


import com.example.adam.tunisia.Model.Entities.Vehicule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IVehicule {

    @GET("rest/vehicules/crud")
    Call<List<Vehicule>> getVehicules();

}
