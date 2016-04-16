package com.example.adam.tunisia.Model.Rest;

import android.content.Context;
import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne_Horaire;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Model.Entities.Station_Ligne_Horaire;
import com.example.adam.tunisia.Model.Rest.Interfaces.IStation_Ligne_Horaires;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitStation_Ligne_Horaires {

    private final Context context;

    private static final String URL = "http://192.168.1.6:8083/projet_pfe_serveur/";
    private static final String TAG = "RetrofitStation_Ligne_Horaires";

    public RetrofitStation_Ligne_Horaires(Context context) {
        this.context = context;
    }

    public void getStation_Lignes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IStation_Ligne_Horaires service = retrofit.create(IStation_Ligne_Horaires.class);

        Call<List<Station_Ligne>> call = service.getStation_Lignes_Horaires();

        call.enqueue(new Callback<List<Station_Ligne>>() {

            @Override
            public void onResponse(Call<List<Station_Ligne>> call, Response<List<Station_Ligne>> response) {
                try {

                    DBAdapterStation_Ligne myDb;
                    DBAdapterStation_Ligne_Horaire myDbH;

                    myDb = new DBAdapterStation_Ligne(context);
                    myDbH = new DBAdapterStation_Ligne_Horaire(context);

                    myDb.open();
                    myDbH.open();
                    myDb.deleteAll();
                    myDbH.deleteAll();

                    for(int i=0;i<response.body().size();i++) {

                        Station_Ligne S = response.body().get(i);

                        List<String> L = response.body().get(i).getHORAIRES();
                        for(String X  : L){
                            myDbH.createStation_Ligne_Horaire(new Station_Ligne_Horaire(S,X));
                        }

                        myDb.createStation_Ligne(S);
                    }

                    myDb.close();
                    myDbH.close();


                }catch (Exception e){
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<List<Station_Ligne>> call, Throwable t) {

                t.printStackTrace();
            }

        });

    }

}
