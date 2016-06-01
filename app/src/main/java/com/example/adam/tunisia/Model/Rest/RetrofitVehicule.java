package com.example.adam.tunisia.Model.Rest;


import android.content.Context;
import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterVehicule;
import com.example.adam.tunisia.Model.Entities.Vehicule;
import com.example.adam.tunisia.Model.Rest.Interfaces.IVehicule;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adam on 12/04/2016.
 */
public class RetrofitVehicule {
    private final Context context;

    private static final String URL =  "http://192.168.1.6:8083/projet_pfe_serveur/" ;
    private static final String TAG = "RetrofitVehicule";

    public RetrofitVehicule(Context context) {
        this.context = context;
    }

    public void getVehicules() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AdapterREST.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IVehicule service = retrofit.create(IVehicule.class);

        Call<List<Vehicule>> call = service.getVehicules();

        call.enqueue(new Callback<List<Vehicule>>() {

            @Override
            public void onResponse(Call<List<Vehicule>> call, Response<List<Vehicule>> response) {
                try {

                    DBAdapterVehicule myDb;
                    myDb = new DBAdapterVehicule(context);

                    myDb.open();
                    myDb.deleteAll();

                    for(int i=0;i<response.body().size();i++) {

                        Vehicule S = response.body().get(i);

                        myDb.createVehicule(S);
                        Log.v("VEHICULE", S.toString());
                    }



                    myDb.close();

                    Log.v(TAG, "getVehicule SUCCEEDED onResponse");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.v(TAG, "getVehicule FAILED onResponse");
                }

            }

            @Override
            public void onFailure(Call<List<Vehicule>> call, Throwable t) {
                Log.v(TAG,"getVehicule FAILED onFailure");
                t.printStackTrace();
            }

        });

    }

}
