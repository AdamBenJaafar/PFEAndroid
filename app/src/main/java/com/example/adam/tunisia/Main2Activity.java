package com.example.adam.tunisia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterSociete
        ;
import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Model.Entities.Societe
        ;
import com.example.adam.tunisia.Model.Rest.Interfaces.ISociete;
import com.example.adam.tunisia.Model.Rest.RetrofitFeedback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Main2Activity extends AppCompatActivity {


    String url = "http://192.168.1.3:8083/projet_pfe_serveur/";

/*
 * Testing commit changes
 * Steps to using the DB:
 * 1. [DONE] Instantiate the DB Adapter
 * 2. [DONE] Open the DB
 * 3. [DONE] use get, insert, delete, .. to change data.
 * 4. [DONE]Close the DB
 */

/**
 * Demo application to show how to use the
 * built-in SQL lite database.
 */


DBAdapterSociete
        myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getReport();
        openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    private void openDB() {
        myDb = new DBAdapterSociete
                (this);

            myDb.open();

    }

    private void closeDB() {
        myDb.close();
    }


    private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.textDisplay);
        textView.setText(message);
    }



    public void onClick_AddRecord(View v) {
        displayText("Clicked add record!");

        Societe
                F = new Societe
                ("SNTRI","Oui","55","SNTRI","Oui","55","SNTRI","Oui","55","SNTRI","Oui");

        long newId = myDb.createSociete
                (F);

        // Query for the record we just added.
        // Use the ID:
        try {
            Societe
                    societe = myDb.getSociete
                    (newId);
            displayText(societe.toString());
        }catch(Exception e){

        }

    }

    public void onClick_ClearAll(View v) {
        displayText("Clicked clear all!");
        myDb.deleteAll();

      //  RetrofitFeedback F = new RetrofitFeedback();
     //   String R=F.toJson(new Feedback("2015-15-02", 1, "55", "44", 5));
     //   Toast.makeText(this,R,Toast.LENGTH_SHORT).show();
     //   F.createFeedback(new Feedback("2015-15-02",1,"55","44",5));
    }

    public void onClick_DisplayRecords(View v) {
        displayText("Clicked display record!");

        ArrayList<Societe
                > A = myDb.getAllSociete
                ();

        String message ="";

        for(int i=0;i<A.size();i++){
            message += A.get(i).toString() + " \n";
        }

        displayText(message);
    }


    void getReport() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISociete service = retrofit.create(ISociete.class);

        Call<Societe> call = service.getWheatherRepor("TRANSTU");

        call.enqueue(new Callback<Societe>() {

            @Override
            public void onResponse(Call<Societe> call, Response<Societe> response) {
                try {

                    String RES="";

                    String city = response.body().getLOGIN();

                    String status = response.body().getFORMEJURIDIQUE();

                    String humidity = response.body().getIDENTIFICATEUR();

                    String pressure = response.message();

                    RES+= "YESSSS" + city + status + humidity+ pressure;

                    displayText(RES);


                    Log.v("OK","SUCCESS");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("NOO", "FAILED");
                }
            }

            @Override
            public void onFailure(Call<Societe> call, Throwable t) {

            }




        });




    }

}










