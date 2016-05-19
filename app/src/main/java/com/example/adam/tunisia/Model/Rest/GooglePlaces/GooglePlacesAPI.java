package com.example.adam.tunisia.Model.Rest.GooglePlaces;

import com.example.adam.tunisia.Model.Entities.GooglePlaces.Example;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GooglePlacesAPI {

    @GET("maps/api/place/nearbysearch/json?location=36.809182,10.148363&radius=1000&key=AIzaSyBze1aC3aLS4CblBPTUeMLZHOXa5d27ydQ")
    Call<Example> getPlacesReport();

}
