package com.example.adam.tunisia.Model.Rest;

import android.content.Context;
import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterActualite;
import com.example.adam.tunisia.Model.Entities.Actualite;
import com.example.adam.tunisia.Model.Rest.Interfaces.IActualite;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adam on 04/04/2016.
 */
public class RetrofitActualite {

    private final Context context;

    private static final String URL = "http://benj-ksayeh.rhcloud.com/";
    private static final String TAG = "RetrofitActualite";

    public RetrofitActualite(Context context) {
        this.context = context;
    }

    public void getActualites() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IActualite service = retrofit.create(IActualite.class);

        Call<List<Actualite>> call = service.getActualites();

        call.enqueue(new Callback<List<Actualite>>() {

            @Override
            public void onResponse(Call<List<Actualite>> call, Response<List<Actualite>> response) {
                try {

                    DBAdapterActualite myDb;
                    myDb = new DBAdapterActualite(context);

                    myDb.open();
                    myDb.deleteAll();

                    for(int i=0;i<response.body().size();i++) {

                        Actualite S = response.body().get(i);

                        myDb.createActualite(S);
                    }



                    myDb.close();

                    Log.v(TAG, "getActualite SUCCEEDED onResponse");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.v(TAG, "getActualite FAILED onResponse");
                }

            }

            @Override
            public void onFailure(Call<List<Actualite>> call, Throwable t) {
                Log.v(TAG,"getActualite FAILED onFailure");
                t.printStackTrace();
            }

        });

    }

}
