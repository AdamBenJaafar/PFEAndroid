package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.adam.tunisia.Model.Entities.Actualite;
import com.example.adam.tunisia.Presenter.Presenters.PActualites;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Adapters.ActualiteRVAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Actualites extends AppCompatActivity {

    private final static String TAG = "Actualites";

    @Bind(R.id.RVActualites)
    RecyclerView RV ;

    // RECYCLERVIEW ADAPTER
    ActualiteRVAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualites);

        //TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Liste des actualités");

        // BINDING
        ButterKnife.bind(this);

        // INITIALISATION
        PActualites PActualites = new PActualites(this);
        PActualites.listerActualites();

        Log.v(TAG,"Actualites onCreate");
    }

    public void afficherActualites(ArrayList<Actualite> LA){

        // RECYCLERVIEW INITIALISATION
        Adapter = new ActualiteRVAdapter(this, LA);

        RV.setAdapter(Adapter);
        RV.setLayoutManager( new LinearLayoutManager(this) );

        Log.v(TAG,"Actualites afficherActualites");
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
