package com.example.adam.tunisia.Presenter.Presenters;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adam.tunisia.Main2Activity;
import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Database.DBAdapterVehicule;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.Model.Entities.Vehicule;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Activities.Home;
import com.example.adam.tunisia.View.Activities.RTMap;
import com.example.adam.tunisia.View.Activities.SCDetails;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class RTMapPresenter  implements  LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private RTMap RTMap;


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    Firebase mRef;

    public String SSociete;
    public String SLigne;

    private LatLng Position;



    public RTMapPresenter(com.example.adam.tunisia.View.Activities.RTMap RTMap) {
        this.RTMap = RTMap;

        mGoogleApiClient = new GoogleApiClient.Builder(RTMap)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

    }


    public void setupFirebase(){

        Firebase.setAndroidContext(RTMap);

        String URL = "https://pfemaps.firebaseio.com/" + SSociete + "/" + SLigne;

        mRef = new Firebase(URL);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
              //  RTMap.animateMarkerTo(RTMap.X,(double)dataSnapshot.child("latitude").getValue(),(double)dataSnapshot.child("longitude").getValue());
                Toast.makeText(RTMap,dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                Log.v("Key",dataSnapshot.getKey());
                Log.v("Value",dataSnapshot.getValue().toString());
                HM.get(dataSnapshot.getKey()).setVisible(true);
                RTMap.animateMarkerTo(HM.get(dataSnapshot.getKey()),(double)dataSnapshot.child("latitude").getValue(),(double)dataSnapshot.child("longitude").getValue());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void selectSociete(){

        DBAdapterSociete A = new DBAdapterSociete(RTMap);
        A.open();

        final ArrayList<CharSequence> L = new ArrayList<CharSequence>();

        for( Societe S : A.getAllSociete() ){
            L.add(S.getIDENTIFICATEUR());
        }


        CharSequence [] items = new CharSequence[L.size()];
        items = L.toArray(items);

        A.close();


        AlertDialog.Builder builder = new AlertDialog.Builder(RTMap);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection

                SSociete = L.get(item).toString();
                selectLigne();


            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private int liglig;
    private String ligtype;

    public void selectLigne(){
        DBAdapterLigne B = new DBAdapterLigne(RTMap);
        B.open();

        final ArrayList<CharSequence> LS = new ArrayList<CharSequence>();
        final ArrayList<Integer> LL = new ArrayList<Integer>();
        final ArrayList<CharSequence> LT = new ArrayList<CharSequence>();

        for( Ligne S : B.getAllLigneBySocieteAller(SSociete) ){
            LS.add(S.getIDENTIFIANT());
            LL.add(S.getROW_ID());
            LT.add(S.getTYPE());
        }


        CharSequence [] itemss = new CharSequence[LS.size()];
        itemss = LS.toArray(itemss);

        final CharSequence [] Z = itemss;

        B.close();


        AlertDialog.Builder builderl = new AlertDialog.Builder(RTMap);
        builderl.setTitle("Make your selection");
        builderl.setItems(Z, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                SLigne=LS.get(item).toString();
                liglig= LL.get(item);
                ligtype = LT.get(item).toString();
                setupFirebase();
                RTMap.drawNetworkOfLine(SLigne);
                selectVehicule();


            }
        });
        AlertDialog alertl = builderl.create();
        alertl.show();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

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

    public void onStop(){
        mGoogleApiClient.disconnect();
    }

    private HashMap<CharSequence,Marker> HM ;

    public void selectVehicule(){
        DBAdapterVehicule B = new DBAdapterVehicule(RTMap);
        B.open();

        HM = new HashMap<CharSequence,Marker>();

        ArrayList<CharSequence> L = new ArrayList<CharSequence>();

        for( Vehicule S : B.getAllVehiculeByLigne(liglig) ){
            L.add(S.getIMMATRICULATION());
            MarkerOptions MO = new MarkerOptions().title("").position(new LatLng(36.8074636,10.1354241));
            switch (ligtype) {
               case "metro": MO.icon(BitmapDescriptorFactory.fromResource(R.mipmap.tram)) ;
               case "bus": MO.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_directions_bus_black_24dp)) ;
                    case "train": MO.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_directions_bus_black_24dp)) ;
            }
            HM.put(S.getIMMATRICULATION(),RTMap.mMap.addMarker(MO));
            HM.get(S.getIMMATRICULATION()).setVisible(false);
        }


        CharSequence [] items = new CharSequence[L.size()];
        items = L.toArray(items);

        final CharSequence [] Z = items;


        Toast.makeText(RTMap, HM.toString(), Toast.LENGTH_LONG).show();

        B.close();

    }


    public void goToPosition(){
        RTMap.animateCamera(Position);
    }


    public void showDialog(){
        // custom dialog
        final Dialog dialog = new Dialog(RTMap);
        dialog.setContentView(R.layout.rtmapdialog);
        dialog.setTitle("Options");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Vous étes en train de vérifier la ligne " + SLigne + " de la societé " + SSociete);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        switch (ligtype) {
            case "metro": image.setImageResource(R.mipmap.tram); break;
            case "bus": image.setImageResource(R.mipmap.ic_directions_bus_black_24dp);break;
            case "train": image.setImageResource(R.mipmap.ic_directions_bus_black_24dp);break;
        }
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                RTMap.recreate();
            }
        });
        Button dialogButtonD = (Button) dialog.findViewById(R.id.dialogButtonLOC);
        // if button is clicked, close the custom dialog
        dialogButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goToPosition();
            }
        });
        Button dialogButtonB = (Button) dialog.findViewById(R.id.dialogButtonBACK);
        // if button is clicked, close the custom dialog
        dialogButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RTMap.retour();
            }
        });


        dialog.show();
    }



}
