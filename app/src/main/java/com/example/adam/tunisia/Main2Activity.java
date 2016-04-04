package com.example.adam.tunisia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterPerturbation
        ;
import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterSociete
        ;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Entities.Perturbation
        ;
import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe
        ;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Rest.Interfaces.ISociete;
import com.example.adam.tunisia.Model.Rest.RetrofitFeedback;
import com.example.adam.tunisia.Model.Rest.RetrofitSociete;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Main2Activity extends AppCompatActivity {

    DBAdapterPerturbation
            myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new DBAdapterPerturbation
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

        Perturbation

                F = new Perturbation

                (5,"Oui","55",new Ligne());

        long newId = myDb.createPerturbation

                (F);

        // Query for the record we just added.
        // Use the ID:
        try {
            Perturbation

                    societe = myDb.getPerturbation

                    (newId);
            displayText(societe.toString());
        }catch(Exception e){

        }

    }

    public void onClick_ClearAll(View v) {
        displayText("Clicked clear all!");
        myDb.deleteAll();
    }

    public void onClick_DisplayRecords(View v) {
        displayText("Clicked display record!");

        ArrayList<Perturbation

                > A = myDb.getAllPerturbation

                ();

        String message ="";

        for(int i=0;i<A.size();i++){
            message += A.get(i).toString() + " \n";
        }

        displayText(message);
    }

}










