package com.example.adam.tunisia.Model.Rest;

import android.content.Context;
import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Rest.Interfaces.ILigne;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLigne {

    private final Context context;

    private static final String URL = "http://benj-ksayeh.rhcloud.com/";
    private static final String TAG = "RetrofitLigne";

    public RetrofitLigne(Context context) {
        this.context = context;
    }

    public void getLignes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ILigne service = retrofit.create(ILigne.class);

        Call<List<Ligne>> call = service.getLignes();

        call.enqueue(new Callback<List<Ligne>>() {

            @Override
            public void onResponse(Call<List<Ligne>> call, Response<List<Ligne>> response) {
                try {

                    DBAdapterLigne myDb;
                    myDb = new DBAdapterLigne(context);

                    myDb.open();
                    myDb.deleteAll();

                    for(int i=0;i<response.body().size();i++) {

                        Ligne S = response.body().get(i);

                        myDb.createLigne(S);
                    }



                    myDb.close();

                    Log.v(TAG, "getLigne SUCCEEDED onResponse");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.v(TAG, "getLigne FAILED onResponse");
                }

            }

            @Override
            public void onFailure(Call<List<Ligne>> call, Throwable t) {
                Log.v(TAG,"getLigne FAILED onFailure");
                t.printStackTrace();
            }

        });

    }

}
