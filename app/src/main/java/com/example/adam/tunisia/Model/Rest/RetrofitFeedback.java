package com.example.adam.tunisia.Model.Rest;

import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Model.Rest.Interfaces.IFeedback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFeedback {

    private static final String URL = "http://192.168.1.3:8083/projet_pfe_serveur/";
    private static final String TAG = "RetrofitFeedback";

    public void postFeedback(Feedback f){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IFeedback apiService = retrofit.create(IFeedback.class);

        Feedback feedbackd = new Feedback("2015-05-02",4,"OK","yyy@email",1);
        Call<Feedback> call = apiService.createUser("TRANSTU",feedbackd);


        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Log.v(TAG, "ONRESPONS");
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                Log.v(TAG, "ONFAILURE");
                t.printStackTrace();
            }


        });
    }

}
