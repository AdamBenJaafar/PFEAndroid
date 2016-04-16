package com.example.adam.tunisia.Model.Rest;

import android.content.Context;
import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Rest.Interfaces.IStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitStation {

    private final Context context;

    private static final String URL = "http://192.168.1.6:8083/projet_pfe_serveur/";
    private static final String TAG = "RetrofitStation";

    public RetrofitStation(Context context) {
        this.context = context;
    }

    public void getStations() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IStation service = retrofit.create(IStation.class);

        Call<List<Station>> call = service.getStations();

        call.enqueue(new Callback<List<Station>>() {

            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                try {

                    DBAdapterStation myDb;
                    myDb = new DBAdapterStation(context);

                    myDb.open();
                    myDb.deleteAll();

                    for(int i=0;i<response.body().size();i++) {

                        Station S = response.body().get(i);

                        myDb.createStation(S);
                    }



                    myDb.close();

                    Log.v(TAG, "getStation SUCCEEDED onResponse");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.v(TAG, "getStation FAILED onResponse");
                }

            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Log.v(TAG,"getStation FAILED onFailure");
                t.printStackTrace();
            }

        });

    }

}
