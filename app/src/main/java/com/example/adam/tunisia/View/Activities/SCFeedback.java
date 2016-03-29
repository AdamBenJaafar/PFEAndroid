package com.example.adam.tunisia.View.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.R;

public class SCFeedback extends AppCompatActivity {

    DBAdapterSociete myDb;

    RatingBar RB = null;
    EditText ET = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmpfeedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Votre avis compte");
        setSupportActionBar(toolbar);


        RB = (RatingBar) findViewById(R.id.ratingBar);
        ET = (EditText) findViewById(R.id.editText);

        openDB();
    }

    public void envoyerFeedback(View view){
        String feedback = ET.getText().toString();
        double rating = RB.getRating();
        myDb.createCompagnie(feedback, rating + "", "okkkkkk");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new DBAdapterSociete(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }



}
