package com.example.adam.tunisia.View.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Entities.Feedback;
import com.example.adam.tunisia.Model.Rest.Interfaces.IFeedback;
import com.example.adam.tunisia.Model.Rest.Interfaces.ISociete;
import com.example.adam.tunisia.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
      //  myDb.createCompagnie(feedback, rating + "", "okkkkkk");

        Log.v("FEEDBACKS","START");

        String URL="http://192.168.1.3:8083/projet_pfe_serveur/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IFeedback  apiService = retrofit.create(IFeedback.class);

        Feedback feedbackd = new Feedback("2015-05-02",4,"OK","@email",1);
        Call<Feedback> call = apiService.createUser("TRANSTU",feedbackd);


        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Log.v("FEEDBACKS","ONRESPONS");
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                Log.v("FEEDBACKS","ONFAILURE");
                t.printStackTrace();
            }


        });
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
