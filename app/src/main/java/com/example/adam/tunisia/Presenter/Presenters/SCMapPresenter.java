package com.example.adam.tunisia.Presenter.Presenters;

import android.util.Log;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Presenter.Helpers.Geohelper;
import com.example.adam.tunisia.View.Activities.SCMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SCMapPresenter {

    private static final String TAG = "SCMapPresenter";

    private SCMap SCMap;

    private DBAdapterLigne myDBLigne;
    private DBAdapterStation_Ligne myDBStation_Ligne;
    private DBAdapterStation myDBStation;

    public SCMapPresenter(SCMap SCMap) {

        this.SCMap = SCMap;
        this.myDBLigne = new DBAdapterLigne(SCMap);
        this.myDBStation_Ligne = new DBAdapterStation_Ligne(SCMap);
        this.myDBStation = new DBAdapterStation(SCMap);

    }

    public void drawNetwork(){

        Log.v(TAG, "Drawing network");

        this.myDBStation.open();
        this.myDBStation_Ligne.open();
        this.myDBLigne.open();


        // CAMERA SETTING VARIABLES
        // -181 & +181 are the bounds of the latlng
        double MaxLNG = -181.0;
        double MinLNG = 181;
        double MaxLAT = -181.0;
        double MinLAT = 181.0;
        int Zoom;


        try {

            List<ArrayList<Station_Ligne>> Network = new ArrayList<ArrayList<Station_Ligne>>();

            // GET LINES
            List<Ligne> LL = myDBLigne.getAllLigneBySocieteAller(SCMap.Societe);
            // FILL LINES
            for( Ligne L : LL) {

                // GET STATIONS
                ArrayList<Station_Ligne> SLL = myDBStation_Ligne.getAllStation_LigneByLigne(L.getROW_ID());
                // FILL STATIONS
                for( Station_Ligne SL : SLL){

                    SL.setSTATION(myDBStation.getStation(SL.getSTATION().getROW_ID()));

                    if(Double.parseDouble(SL.getSTATION().getLATITUDE())>MaxLAT){
                        MaxLAT=Double.parseDouble(SL.getSTATION().getLATITUDE());
                    }
                    if(Double.parseDouble(SL.getSTATION().getLATITUDE())<MinLAT){
                        MinLAT=Double.parseDouble(SL.getSTATION().getLATITUDE());
                    }

                    if(Double.parseDouble(SL.getSTATION().getLONGITUDE())>MaxLNG){
                        MaxLNG=Double.parseDouble(SL.getSTATION().getLONGITUDE());
                    }
                    if(Double.parseDouble(SL.getSTATION().getLONGITUDE())<MinLNG){
                        MinLNG=Double.parseDouble(SL.getSTATION().getLONGITUDE());
                    }

                }

                Collections.sort(SLL);

                Network.add(SLL);

            }

            SCMap.drawNetwork(Network);

            myDBStation_Ligne.close();
            myDBStation.close();
            myDBLigne.close();

        }catch(Exception e){
            Log.v(TAG,"An exception has been thrown");
            e.printStackTrace();
        }

        Zoom = Geohelper.getZoomLevel(Math.max(Geohelper.distFrom(MaxLAT,0,MinLAT,0),Geohelper.distFrom(MaxLNG,0,MinLNG,0)));

        // SETTING CAMERA POSITION
        SCMap.setCameraPosition((MaxLAT+MinLAT)/2,(MaxLNG+MinLNG)/2, Zoom);
    }

}
