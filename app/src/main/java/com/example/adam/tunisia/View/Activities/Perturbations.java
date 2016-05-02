package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.adam.tunisia.Model.Entities.Perturbation;
import com.example.adam.tunisia.Presenter.Presenters.PPerturbations;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Adapters.PerturbationRVAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Perturbations extends AppCompatActivity {

    private final static String TAG = "Perturbations";

    @Bind(R.id.RVPerturbations)
    RecyclerView RV ;

    // RECYCLERVIEW ADAPTER
    PerturbationRVAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perturbations);

        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Liste des perturbations");

        // BINDING
        ButterKnife.bind(this);

        // INITIALISATION
        PPerturbations PPerturbations = new PPerturbations(this);
        PPerturbations.listerPerturbations();

        Log.v(TAG,"Perturbations onCreate");
    }

    public void afficherPerturbations(ArrayList<Perturbation> LP){

        // RECYCLERVIEW INITIALISATION
        Adapter = new PerturbationRVAdapter(this, LP);

        RV.setAdapter(Adapter);
        RV.setLayoutManager( new LinearLayoutManager(this) );

        Log.v(TAG,"Perturbations afficherPerturbations");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
