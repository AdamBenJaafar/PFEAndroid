package com.example.adam.tunisia.Presenter.Presenters;

import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterActualite;
import com.example.adam.tunisia.Model.Entities.Actualite;
import com.example.adam.tunisia.View.Activities.Actualites;

import java.util.ArrayList;

public class PActualites {

    private static final String TAG = "PActualites";

    private Actualites Actualites;

    private DBAdapterActualite DBAA;

    public PActualites(com.example.adam.tunisia.View.Activities.Actualites actualites) {

        this.Actualites = actualites;
        this.DBAA = new DBAdapterActualite(Actualites);

        Log.v(TAG,"CONSTRUCTOR");
    }

    public void listerActualites(){

        DBAA.open();

        try {

            ArrayList<Actualite> LA = DBAA.getAllActualite();

            Actualites.afficherActualites(LA);

            Log.v(TAG,"listerActualites DB SUCCEEDED");

        }catch(Exception e){

            e.printStackTrace();
            Log.v(TAG,"listerActualites DB FAILED");

        }

        DBAA.close();

        Log.v(TAG,"listerActualites");
    }

}
