package com.example.adam.tunisia.Model.Rest.Interfaces;

import com.example.adam.tunisia.Model.Entities.Societe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISociete {

  @GET("rest/societes/crud")
  Call<List<Societe>> getWheatherReport();

  @GET("rest/societes/crud/{T}")
  Call<Societe> getWheatherRepor(@Path("T") String T);

}
