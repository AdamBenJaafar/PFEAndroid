package com.example.adam.tunisia.Model.Rest.GooglePlaces;

import com.example.adam.tunisia.Model.Entities.GooglePlaces.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesAPI {

    @GET("maps/api/place/nearbysearch/json?radius=1000&key=AIzaSyBze1aC3aLS4CblBPTUeMLZHOXa5d27ydQ")
    Call<Example> getPlacesReport(@Query("location") String LATLNG);

}
