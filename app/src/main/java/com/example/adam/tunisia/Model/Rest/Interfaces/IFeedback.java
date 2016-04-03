package com.example.adam.tunisia.Model.Rest.Interfaces;

import com.example.adam.tunisia.Model.Entities.Feedback;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Adam on 01/04/2016.
 */
public interface IFeedback {

    @POST("rest/feedbacks/crud/{IDENTIFICATEUR}")
    Call<Feedback> createUser(@Path("IDENTIFICATEUR") String IDENTIFICATEUR, @Body Feedback feedback);

}
