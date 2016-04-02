package com.example.adam.tunisia.Model.Rest;

import android.content.Context;
import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.Model.Rest.Interfaces.ISociete;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSociete {

    private final Context context;

    private static final String URL = "http://192.168.1.3:8083/projet_pfe_serveur/";
    private static final String TAG = "RetrofitSociete";

    public RetrofitSociete(Context context) {
        this.context = context;
    }

    public void getSocietes(String id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISociete service = retrofit.create(ISociete.class);

        Call<Societe> call = service.getSociete("TRANSTU");

        call.enqueue(new Callback<Societe>() {

            @Override
            public void onResponse(Call<Societe> call, Response<Societe> response) {
                try {

                    DBAdapterSociete myDb;
                    myDb = new DBAdapterSociete(context);

                    myDb.open();

                    Societe S = new Societe();

                    String city = response.body().getLOGIN();
                    S.setLOGIN(city);
                    String status = response.body().getFORMEJURIDIQUE();
                    S.setFORMEJURIDIQUE(status);
                    String humidity = response.body().getIDENTIFICATEUR();
                    S.setIDENTIFICATEUR(humidity);


                    myDb.deleteAll();
                    myDb.createSociete(S);

                    myDb.close();

                    Log.v(TAG, "getSociete SUCCEEDED onResponse");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.v(TAG, "getSociete FAILED onResponse");
                }

            }

            @Override
            public void onFailure(Call<Societe> call, Throwable t) {
                Log.v(TAG,"getSociete FAILED onFailure");
            }

        });

    }

}
