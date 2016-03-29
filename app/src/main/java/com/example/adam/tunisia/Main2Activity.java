package com.example.adam.tunisia;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import com.example.adam.tunisia.Model.Database.DBAdapterFeedback;
import com.example.adam.tunisia.Model.Entities.Feedback;

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


DBAdapterFeedback myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       // openDB();
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    private void openDB() {
        myDb = new DBAdapterFeedback(this);

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

      //  Feedback F = new Feedback(0,"SNTRI","Bien",5,"Oui","J'adore");

      //  long newId = myDb.createFeedback(F);

        // Query for the record we just added.
        // Use the ID:
        try {
        //    Feedback feedback = myDb.getFeedback(newId);
        //    displayText(feedback.toString());
        }catch(Exception e){

        }

    }

    public void onClick_ClearAll(View v) {
        displayText("Clicked clear all!");
        myDb.deleteAll();
    }

    public void onClick_DisplayRecords(View v) {
        displayText("Clicked display record!");

        ArrayList<Feedback> A = myDb.getAllFeedback();

        String message ="";

        for(int i=0;i<A.size();i++){
            message += A.get(i).toString() + " \n";
        }

        displayText(message);
    }
*/
}










