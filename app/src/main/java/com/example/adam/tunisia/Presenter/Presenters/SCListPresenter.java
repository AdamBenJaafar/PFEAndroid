package com.example.adam.tunisia.Presenter.Presenters;

import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterPerturbation;
import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Perturbation;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.View.Activities.Perturbations;
import com.example.adam.tunisia.View.Activities.Soceites;

import java.util.ArrayList;

public class SCListPresenter {

    private static final String TAG = "PPerturbations";

    private Soceites Perturbations;

    private DBAdapterSociete DBAP;
    private DBAdapterLigne DBAL;

    public SCListPresenter(com.example.adam.tunisia.View.Activities.Soceites perturbations) {

        this.Perturbations = perturbations;
        this.DBAP = new DBAdapterSociete(Perturbations);
        this.DBAL = new DBAdapterLigne(Perturbations);

        Log.v(TAG,"CONSTRUCTOR");
    }

    public void listerPerturbations(){

        DBAP.open();
        DBAL.open();

        try {

            ArrayList<Societe> LP = DBAP.getAllSociete();
            /*for (Societe P : LP) {

                Ligne L = DBAL.getLigne( P.getLIG().getIDENTIFIANT() );
                P.setLIG(L);

            }*/

            Perturbations.afficherPerturbations(LP);
            Log.v(TAG,"listerPerturbations DB SUCCEEDED");

        }catch(Exception e){

            e.printStackTrace();
            Log.v(TAG,"listerPerturbations DB FAILED");

        }

        DBAP.close();
        DBAL.close();

        Log.v(TAG,"listerPerturbations");
    }

}
