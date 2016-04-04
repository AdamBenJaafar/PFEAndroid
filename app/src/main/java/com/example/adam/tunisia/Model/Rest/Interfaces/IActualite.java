package com.example.adam.tunisia.Model.Rest.Interfaces;

import com.example.adam.tunisia.Model.Entities.Actualite;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IActualite {

    @GET("rest/actualites/crud")
    Call<List<Actualite>> getActualites();

}
