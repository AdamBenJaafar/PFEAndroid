package com.example.adam.tunisia.Model.Rest;

import android.content.Context;
import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterPerturbation;
import com.example.adam.tunisia.Model.Entities.Perturbation;
import com.example.adam.tunisia.Model.Rest.Interfaces.IPerturbation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitPerturbation {

    private final Context context;

    private static final String URL = "http://192.168.1.6:8083/projet_pfe_serveur/" ;
    private static final String TAG = "RetrofitPerturbation";

    public RetrofitPerturbation(Context context) {
        this.context = context;
    }

    public void getPerturbations() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AdapterREST.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPerturbation service = retrofit.create(IPerturbation.class);

        Call<List<Perturbation>> call = service.getPerturbations();

        call.enqueue(new Callback<List<Perturbation>>() {

            @Override
            public void onResponse(Call<List<Perturbation>> call, Response<List<Perturbation>> response) {
                try {

                    DBAdapterPerturbation myDb;
                    myDb = new DBAdapterPerturbation(context);

                    myDb.open();
                    myDb.deleteAll();

                    for(int i=0;i<response.body().size();i++) {

                        Perturbation S = response.body().get(i);
                        myDb.createPerturbation(S);

                    }



                    myDb.close();

                    Log.v(TAG, "getPerturbation SUCCEEDED onResponse");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.v(TAG, "getPerturbation FAILED onResponse");
                }

            }

            @Override
            public void onFailure(Call<List<Perturbation>> call, Throwable t) {
                Log.v(TAG,"getPerturbation FAILED onFailure");
                t.printStackTrace();
            }

        });

    }

}
