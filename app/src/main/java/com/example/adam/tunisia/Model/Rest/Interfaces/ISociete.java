package com.example.adam.tunisia.Model.Rest.Interfaces;

import com.example.adam.tunisia.Model.Entities.Societe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISociete {

  @GET("rest/societes/crud")
  Call<List<Societe>> getSocietes();

  @GET("rest/societes/crud/{IDENTIFICATEUR}")
  Call<Societe> getSociete(@Path("IDENTIFICATEUR") String IDENTIFICATEUR);

}
