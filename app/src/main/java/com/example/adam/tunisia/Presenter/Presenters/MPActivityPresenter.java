package com.example.adam.tunisia.Presenter.Presenters;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Presenter.Helpers.Geohelper;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Activities.MPActivity;
import com.example.adam.tunisia.View.Activities.SCMap;
import com.example.adam.tunisia.View.Adapters.LignesDialogAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Adam on 28/03/2016.
 */
public class MPActivityPresenter  implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    // Google localisation
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public LatLng Position;

    private static final String TAG = "MPActivityPresenter";

    private com.example.adam.tunisia.View.Activities.MPActivity MPActivity;

    private DBAdapterLigne myDBLigne;
    private DBAdapterStation_Ligne myDBStation_Ligne;
    private DBAdapterStation myDBStation;
    private DBAdapterSociete myDBSociete;

    public MPActivityPresenter(com.example.adam.tunisia.View.Activities.MPActivity mp){
        this.MPActivity = mp;
        this.myDBLigne = new DBAdapterLigne(mp);
        this.myDBStation_Ligne = new DBAdapterStation_Ligne(mp);
        this.myDBStation = new DBAdapterStation(mp);
        this.myDBSociete = new DBAdapterSociete(mp);

        this.myDBStation.open();
        this.myDBStation_Ligne.open();
        this.myDBLigne.open();
        this.myDBSociete.open();

        // Google localisation initialization
        mGoogleApiClient = new GoogleApiClient.Builder(MPActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        Position = new LatLng(36,10);

    }

    public void drawNetwork(){

        Log.v(TAG, "Drawing network");

        // CAMERA SETTING VARIABLES
        // -181 & +181 are the bounds of the latlng
        double MaxLNG = -181.0;
        double MinLNG = 181;
        double MaxLAT = -181.0;
        double MinLAT = 181.0;
        int Zoom;


        try {



            // GET LINES
            ArrayList<Societe> LS = myDBSociete.getAllSociete();

            for(int i =0; i < LS.size() ; i++ ) {

                List<ArrayList<Station_Ligne>> Network = new ArrayList<ArrayList<Station_Ligne>>(); //*******************

                List<Ligne> LL = myDBLigne.getAllLigneBySocieteAller(LS.get(i).getIDENTIFICATEUR());
                // FILL LINES
                for (Ligne L : LL) {

                    // GET STATIONS
                    ArrayList<Station_Ligne> SLL = myDBStation_Ligne.getAllStation_LigneByLigne(L.getROW_ID());
                    // FILL STATIONS
                    for (Station_Ligne SL : SLL) {

                        SL.setSTATION(myDBStation.getStation(SL.getSTATION().getROW_ID()));

                        if (Double.parseDouble(SL.getSTATION().getLATITUDE()) > MaxLAT) {
                            MaxLAT = Double.parseDouble(SL.getSTATION().getLATITUDE());
                        } else if (Double.parseDouble(SL.getSTATION().getLATITUDE()) < MinLAT) {
                            MinLAT = Double.parseDouble(SL.getSTATION().getLATITUDE());
                        }

                        if (Double.parseDouble(SL.getSTATION().getLONGITUDE()) > MaxLNG) {
                            MaxLNG = Double.parseDouble(SL.getSTATION().getLONGITUDE());
                        }
                        if (Double.parseDouble(SL.getSTATION().getLONGITUDE()) < MinLNG) {
                            MinLNG = Double.parseDouble(SL.getSTATION().getLONGITUDE());
                        }

                    }

                    Collections.sort(SLL);

                    Network.add(SLL);

                }

                MPActivity.drawNetwork(Network,i);
            }

            myDBStation_Ligne.close();
            myDBStation.close();
            myDBLigne.close();
            myDBSociete.close();

        }catch(Exception e){
            Log.v(TAG,"An exception has been thrown");
            e.printStackTrace();
        }

        Zoom = Geohelper.getZoomLevel(Math.max(Geohelper.distFrom(MaxLAT,0,MinLAT,0),Geohelper.distFrom(MaxLNG,0,MinLNG,0)));

        // SETTING CAMERA POSITION
        MPActivity.setCameraPosition((MaxLAT+MinLAT)/2,(MaxLNG+MinLNG)/2, Zoom);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(2000);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Position = new LatLng(location.getLatitude(),location.getLongitude());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void goToPosition(){
        MPActivity.animateCamera(Position);
    }



}
