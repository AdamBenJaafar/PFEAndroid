package com.example.adam.tunisia.Model.Rest.Interfaces;

import com.example.adam.tunisia.Model.Entities.Ligne;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ILigne {

    @GET("rest/lignes/crud")
    Call<List<Ligne>> getLignes();

}
