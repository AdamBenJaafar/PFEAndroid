package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Adapters.PerturbationRVAdapter;
import com.example.adam.tunisia.View.Adapters.StationsRVAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Stations extends AppCompatActivity {

    @Bind(R.id.RVStations)
    RecyclerView RV;


    String Societe;
    long SocID;

    StationsRVAdapter Adapter;

    private DBAdapterStation DBAS;
    private DBAdapterStation_Ligne DBASL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);


        Societe = getIntent().getStringExtra("Societe");
        SocID = getIntent().getLongExtra("SocID",0);


        Log.v("RECIEVED",Societe );
        Log.v("REVIEVED", SocID +"" );

        String ligne = getIntent().getStringExtra("ligne");
        int ligneid = getIntent().getIntExtra("ligneid",0);
        Toast.makeText(this,ligne,Toast.LENGTH_LONG).show();

        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(ligne);

        getSupportActionBar().setIcon(R.mipmap.tram);


        ButterKnife.bind(this);


        DBASL = new DBAdapterStation_Ligne(this);
        DBAS = new DBAdapterStation(this);
        DBAS.open();
        DBASL.open();


        List <Station> LS = DBAS.getAllStation();
        List <Station_Ligne> LSL = DBASL.getAllStation_LigneByLigne(ligneid);

        LS.clear();

        for ( Station_Ligne SL : LSL ){
            LS.add(DBAS.getStation(SL.getSTATION().getROW_ID()));
        }

        // RECYCLERVIEW INITIALISATION
        Adapter = new StationsRVAdapter(this, LS, ligneid);

        RV.setAdapter(Adapter);
        RV.setLayoutManager( new LinearLayoutManager(this) );


        DBAS.close();
        DBASL.close();




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:


                Log.v("SENT",Societe );
                Log.v("SENT", SocID +"" );

                Intent intent = new Intent(this, Soceites.class);
                intent.putExtra("SocIDEN",Societe);
                intent.putExtra("SocID",SocID);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
