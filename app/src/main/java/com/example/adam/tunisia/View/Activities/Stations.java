package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Adapters.PerturbationRVAdapter;
import com.example.adam.tunisia.View.Adapters.StationsRVAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Stations extends AppCompatActivity {

    @Bind(R.id.RVStations)
    RecyclerView RV;


    StationsRVAdapter Adapter;

    private DBAdapterStation DBAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);

        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Stations");


        ButterKnife.bind(this);


        String ligne = getIntent().getStringExtra("ligne");
        Toast.makeText(this,ligne,Toast.LENGTH_LONG).show();

        DBAS = new DBAdapterStation(this);
        DBAS.open();

        List <Station> LS = DBAS.getAllStation();

        // RECYCLERVIEW INITIALISATION
        Adapter = new StationsRVAdapter(this, LS);

        RV.setAdapter(Adapter);
        RV.setLayoutManager( new LinearLayoutManager(this) );


        DBAS.close();




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, SCDetails.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
