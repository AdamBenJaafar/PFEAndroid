package com.example.adam.tunisia;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import com.example.adam.tunisia.Model.Database.DBAdapterSociete
        ;
import com.example.adam.tunisia.Model.Entities.Societe
        ;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {


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

}










