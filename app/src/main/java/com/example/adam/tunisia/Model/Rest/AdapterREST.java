package com.example.adam.tunisia.Model.Rest;

import android.content.Context;

public class AdapterREST {

    private final Context context;

    public static String URL = /* "http://192.168.43.17:8083/projet_pfe_serveur/"; // 1.6 */ "http://projet-pfe16.rhcloud.com/" ;

    public AdapterREST(Context context) {
        this.context = context;
    }

    /* ***************************************************************
                        DATA UPDATE METHODS
    *************************************************************** */

    public void update(){
        RetrofitSociete RSociete = new RetrofitSociete(context);
        RSociete.getSocietes();
        RetrofitStation RStation = new RetrofitStation(context);
        RStation.getStations();
        RetrofitLigne RLigne = new RetrofitLigne(context);
        RLigne.getLignes();
        RetrofitActualite RActualite = new RetrofitActualite(context);
        RActualite.getActualites();
        RetrofitPerturbation RPerturbation = new RetrofitPerturbation(context);
        RPerturbation.getPerturbations();
        RetrofitStation_Ligne_Horaires RSLH = new RetrofitStation_Ligne_Horaires(context);
        RSLH.getStation_Lignes();
        RetrofitVehicule RV = new RetrofitVehicule(context);
        RV.getVehicules();
    }

}
