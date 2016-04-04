package com.example.adam.tunisia.Model.Rest.Interfaces;

import com.example.adam.tunisia.Model.Entities.Perturbation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IPerturbation {

    @GET("rest/perturbations/crud")
    Call<List<Perturbation>> getPerturbations();

}
