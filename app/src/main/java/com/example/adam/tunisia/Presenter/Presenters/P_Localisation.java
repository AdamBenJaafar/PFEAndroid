package com.example.adam.tunisia.Presenter.Presenters;

import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Database.DBAdapterVehicule;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Model.Entities.Vehicule;
import com.example.adam.tunisia.Presenter.Helpers.GeoHelper;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Activities.Localisation;
import com.example.adam.tunisia.View.Adapters.DonationAdapter;

import com.example.adam.tunisia.View.Adapters.LignesDialogAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class P_Localisation implements  LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    // TAG
    private final static String TAG = "P_Localisation";

    // VIEW : Activity
    private Localisation Localisation;

    // Google localisation
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    // Firebase
    Firebase mRef;
    boolean Synchronize;

    // Location configuration
    public String SelectedSociete;
    public String SelectedLigne;
    private LatLng Position;

    private int liglig;
    private String ligtype;

    // Vehicule markers
    private HashMap<CharSequence,Marker> HM ;

    // CONSTRUCTOR
    public P_Localisation(Localisation Localisation) {
        this.Localisation = Localisation;

        // Google localisation initialization
        mGoogleApiClient = new GoogleApiClient.Builder(Localisation)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

    }

    // LISTENING TO THE SELECTED LINE VEHICULE VARIATIONS
    public void setupFirebase(){

        Firebase.setAndroidContext(Localisation);

        String URL = "https://pfemaps.firebaseio.com/" + SelectedSociete + "/" + SelectedLigne;

        mRef = new Firebase(URL);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String movingVehicule = dataSnapshot.getKey();
                Log.v(TAG,movingVehicule);
                Log.v(TAG,HM.toString());
                if(HM.containsKey(movingVehicule)) {

                    double vehiculeLat = (double) dataSnapshot.child("latitude").getValue();
                    double vehiculeLng = (double) dataSnapshot.child("longitude").getValue();

                    Marker vehiculeMarker = HM.get(movingVehicule);
                    vehiculeMarker.setVisible(true);

                    // Animating the vehicule marker to the current position
                    Localisation.animateMarkerTo(HM.get(movingVehicule), vehiculeLat, vehiculeLng);
                }
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
        Synchronize = true;
    }

    // SELECT TRACKING TYPE
    public void selectTracking(){

        final LovelyCustomDialog LC = new LovelyCustomDialog(Localisation);

                    LC
                .setView(R.layout.locationdialog)
                .setTopColor(ContextCompat.getColor(Localisation,R.color.colorPrimary))
                .setIcon(R.drawable.google_maps)
                .setCancelable(false)
                .setTitle("Que type de ligne ?")
               // .configureView()
               // .setInstanceStateManager(/* ... */)
                            .setListener(R.id.BSociete, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selectSociete();
                                    LC.dismiss();
                                }
                            })
                            .setListener(R.id.BProche, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(Position!=null) {
                                        selectCloseLigne();
                                        LC.dismiss();
                                    }
                                    else {
                                        Toast.makeText(Localisation, "Activez la localisation", Toast.LENGTH_LONG).show();
                                        LC.dismiss();
                                        selectTracking();
                                    }

                                }
                            })
                            .show();

    }

    // ASKING FOR THE SOCIETY
    public void selectSociete(){

        DBAdapterSociete A = new DBAdapterSociete(Localisation);
        A.open();

       // final ArrayList<CharSequence> L = new ArrayList<CharSequence>();

       /* for( Societe S : A.getAllSociete() ){
            L.add(S.getIDENTIFICATEUR());
        }


        CharSequence [] items = new CharSequence[L.size()];
        items = L.toArray(items);
*/



      /*  AlertDialog.Builder builder = new AlertDialog.Builder(Localisation);
        builder.setTitle("Sectionnez une société");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection

                SelectedSociete = L.get(item).toString();
                selectLigne();


            }
        });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();*/


        final ArrayList<Societe> LL = new ArrayList<Societe>();
        LL.add(new Societe("OUI"));
        LL.add(new Societe("NIEN"));
        LL.add(new Societe("MBY"));
        LL.add(new Societe("WNOT"));

        final ArrayList<Societe> L = A.getAllSociete();

        ArrayAdapter<Societe> adapter = new DonationAdapter(Localisation, L );
        new LovelyChoiceDialog(Localisation)
                .setTopColor(ContextCompat.getColor(Localisation,R.color.colorPrimary))
                .setTitle("Choisissez une société")
                .setIcon(R.drawable.bank)
                .setTitleGravity(50)
                .setItems(adapter, new LovelyChoiceDialog.OnItemSelectedListener<Societe>() {
                    @Override
                    public void onItemSelected(int position, Societe item) {
                        SelectedSociete = L.get(position).getIDENTIFICATEUR();
                        selectLigne();

                    }
                })

                .setCancelable(false)
                .show();
        A.close();

    }

    // ASKING FOR THE LINE
    public void selectLigne(){
        DBAdapterLigne B = new DBAdapterLigne(Localisation);
        B.open();

       /* final ArrayList<CharSequence> LS = new ArrayList<CharSequence>();
        final ArrayList<Integer> LL = new ArrayList<Integer>();
        final ArrayList<CharSequence> LT = new ArrayList<CharSequence>();

        for( Ligne S : B.getAllLigneBySocieteAller(SelectedSociete) ){
            LS.add(S.getIDENTIFIANT());
            LL.add(S.getROW_ID());
            LT.add(S.getTYPE());
        }


        CharSequence [] itemss = new CharSequence[LS.size()];
        itemss = LS.toArray(itemss);

        final CharSequence [] Z = itemss;*/

        final ArrayList<Ligne> LL = B.getAllLigneBySocieteAller(SelectedSociete);

        B.close();


        ArrayAdapter<Ligne> adapter = new LignesDialogAdapter(Localisation, LL );
        new LovelyChoiceDialog(Localisation)
                .setTopColor(ContextCompat.getColor(Localisation,R.color.colorPrimary))
                .setTitle("Choisissez une ligne de "+ SelectedSociete)
                .setIcon(R.mipmap.ligne)
                .setTitleGravity(50)
                .setItems(adapter, new LovelyChoiceDialog.OnItemSelectedListener<Ligne>() {
                    @Override
                    public void onItemSelected(int position, Ligne item) {

                        SelectedLigne = LL.get(position).getIDENTIFIANT();
                        liglig= LL.get(position).getROW_ID();
                        ligtype = LL.get(position).getTYPE();

                        setupFirebase();
                        Localisation.drawNetworkOfLine(SelectedLigne,SelectedSociete);
                        selectVehicule();


                    }
                })

                .setCancelable(false)
                .show();


       /* AlertDialog.Builder builderl = new AlertDialog.Builder(Localisation);
        builderl.setTitle("Selectionnez une ligne");
        builderl.setItems(Z, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                SelectedLigne=LS.get(item).toString();
                liglig= LL.get(item);
                ligtype = LT.get(item).toString();


                setupFirebase();
                Localisation.drawNetworkOfLine(SelectedLigne, SelectedSociete);
                selectVehicule();

               // selectTracking();

            }
        });
        AlertDialog alertl = builderl.create();
        alertl.setCanceledOnTouchOutside(false);
        alertl.show();*/

    }

    // SELECT STATTIONS
    public void selectCloseLigne(){

        DBAdapterStation DBAS = new DBAdapterStation(Localisation);
        DBAdapterLigne DBAL = new DBAdapterLigne(Localisation);
        DBAdapterStation_Ligne DBASL = new DBAdapterStation_Ligne(Localisation);

        DBAS.open();
        DBAL.open();
        DBASL.open();

        ArrayList<StationClose> LSC = new ArrayList();

        for ( Station S : DBAS.getAllStation() ){
            StationClose SC = new StationClose(GeoHelper.distFrom(Position.latitude,Position.longitude,Double.parseDouble(S.getLATITUDE()),Double.parseDouble(S.getLONGITUDE())),S.getROW_ID());
            SC.setNom(S.getNOM());
            Log.v(TAG,SC.toString());
            LSC.add(SC);
        }

        Collections.sort(LSC);

        final ArrayList<Ligne> LL = new ArrayList();
        ArrayList<Integer> LID = new ArrayList();

        for(int i=0;i < 5;i++){
            Log.v(TAG,"Getting from staion" + LSC.get(i).getNom() );
            String LignesPassantesParCetteStation= "";
            for( Station_Ligne SL :  DBASL.getAllStation_LigneByStation(LSC.get(i).getId()) ){
                Log.v(TAG,"Getting from staion_ligne" + SL.getROW_ID() );
                if(!LID.contains(SL.getLIGNE().getROW_ID())){
                    LID.add(SL.getLIGNE().getROW_ID());
                    Log.v(TAG,"added");
                }
            LignesPassantesParCetteStation += DBAL.getLigne(SL.getLIGNE().getROW_ID()).getIDENTIFIANT() +"\n";
            }

           // markStation(LSC.get(i).getId(), LignesPassantesParCetteStation);
        }

        for( int X : LID ){
            Ligne LLL = DBAL.getLigne(X);
            LL.add(LLL);
        }



        DBAS.close();
        DBAL.close();
        DBASL.close();


        ArrayAdapter<Ligne> adapter = new LignesDialogAdapter(Localisation, LL );
        new LovelyChoiceDialog(Localisation)
                .setTopColor(ContextCompat.getColor(Localisation,R.color.colorPrimary))
                .setTitle("Choisissez une ligne")
                .setIcon(R.mipmap.ligne)
                .setTitleGravity(50)
                .setItems(adapter, new LovelyChoiceDialog.OnItemSelectedListener<Ligne>() {
                    @Override
                    public void onItemSelected(int position, Ligne item) {

                        SelectedLigne = LL.get(position).getIDENTIFIANT();
                        Log.v(TAG, " SelectedLigne : "+ SelectedLigne);
                        liglig= LL.get(position).getROW_ID();
                        Log.v(TAG, " liglig : "+ liglig);
                        ligtype = LL.get(position).getTYPE();
                        Log.v(TAG, " ligtype : "+ ligtype);

                        setupFirebase();
                        Localisation.drawNetworkOfLine(SelectedLigne,LL.get(position).getSOC().getIDENTIFICATEUR());
                        selectVehicule();

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(Position)
                                .zoom(13)
                                .build();
                        Localisation.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                    }
                })

                .setCancelable(false)
                .show();


    }

    // SELECT THE VEHICLES OF THE REQUESTED LINE
    public void selectVehicule(){

        DBAdapterVehicule B = new DBAdapterVehicule(Localisation);
        B.open();

        HM = new HashMap<CharSequence,Marker>();

        ArrayList<CharSequence> L = new ArrayList<CharSequence>();

        for( Vehicule S : B.getAllVehiculeByLigne(liglig) ){
            L.add(S.getIMMATRICULATION());
            MarkerOptions MO = new MarkerOptions().title("").position(new LatLng(36.8074636,10.1354241));
            switch (ligtype) {
               case "metro": MO.icon(BitmapDescriptorFactory.fromResource(R.mipmap.tram)) ; break;
               case "bus": MO.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_directions_bus_black_24dp)) ; break;
                    case "train": MO.icon(BitmapDescriptorFactory.fromResource(R.drawable.train)) ; break;
            }
            HM.put(S.getIMMATRICULATION(),Localisation.mMap.addMarker(MO));
            HM.get(S.getIMMATRICULATION()).setVisible(false);
        }

        B.close();

    }

    public void markStation(int ID, String desc){

        DBAdapterStation DBAS = new DBAdapterStation(Localisation);
        DBAS.open();

        Station S = DBAS.getStation(ID);

        DBAS.close();

        LatLng POS = new LatLng(Double.parseDouble(S.getLATITUDE()),Double.parseDouble(S.getLONGITUDE()));

        MarkerOptions MO = new MarkerOptions().title(S.getNOM()).position(POS).icon(BitmapDescriptorFactory.fromResource(R.mipmap.stat));
        M =Localisation.mMap.addMarker(MO);

    }

    /* ***************************************************************
                        DIALOG METHODS
    *************************************************************** */

    public void showDialog(){

        final LovelyCustomDialog LC = new LovelyCustomDialog(Localisation);

        LC
                .setView(R.layout.rtmapdialog)
                .setTopColor(ContextCompat.getColor(Localisation,R.color.colorPrimary))
                .setIcon(R.drawable.google_maps)
                .setCancelable(false)
                .setMessage("Vous étes en train de vérifier la ligne " + SelectedLigne + " de la societé " + SelectedSociete)
                .setTitle("Que voulez vous faire ?")
                .setListener(R.id.dialogButtonLOC, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectSociete();
                        LC.dismiss();
                        Localisation.resetup();
                    }
                })
                .setListener(R.id.dialogButtonBACK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LC.dismiss();
                        goToPosition();
                    }
                })
                .setListener(R.id.dialogButtonOK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LC.dismiss();
                        //Localisation.recreate();
                        Synchronize = false;
                        Localisation.resetup();
                        selectCloseLigne();

                    }
                })
                .show();


        // custom dialog
       /* final Dialog dialog = new Dialog(Localisation);
        dialog.setContentView(R.layout.rtmapdialog);
        dialog.setTitle("Options");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Vous étes en train de vérifier la ligne " + SelectedLigne + " de la societé " + SelectedSociete);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        switch (ligtype) {
            case "metro": image.setImageResource(R.mipmap.tram); break;
            case "bus": image.setImageResource(R.mipmap.ic_directions_bus_black_24dp);break;
            case "train": image.setImageResource(R.drawable.train);break;
        }
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //Localisation.recreate();
                Synchronize = false;
                selectCloseLigne();
                Localisation.resetup();
            }
        });
        Button dialogButtonD = (Button) dialog.findViewById(R.id.dialogButtonLOC);
        // if button is clicked, close the custom dialog
        dialogButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSociete();
                dialog.dismiss();
                Localisation.resetup();
            }
        });
        Button dialogButtonB = (Button) dialog.findViewById(R.id.dialogButtonBACK);
        // if button is clicked, close the custom dialog
        dialogButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goToPosition();
            }
        });


        dialog.show();*/
    }

    public void goToPosition(){
        Localisation.animateCamera(Position);
    }

    /* ***************************************************************
                        LOCATION METHODS
    *************************************************************** */

    Marker M;

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

        MarkerOptions MO = new MarkerOptions().title("Vous étes ici").position(Position).icon(BitmapDescriptorFactory.fromResource(R.mipmap.tracking));
        if(M!=null)
        M.remove();

        M =Localisation.mMap.addMarker(MO);
        M.setVisible(true);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

     /* **************************************************************
                        LIFECYCLE METHODS
     ************************************************************** */

    public void onStop(){
        mGoogleApiClient.disconnect();
    }

    public class StationClose implements Comparable<StationClose>{

        public String nom;
        public double distance ;
        public int Id;

        public StationClose(double distance, int id) {
            this.distance = distance;
            Id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        @Override
        public String toString() {
            return "StationClose{" +
                    "distance=" + distance +
                    ", Id=" + Id +
                    '}';
        }

        @Override
        public int compareTo(StationClose another) {
            if(another.getDistance()>this.getDistance()){
                return -1;
            }else{
                return 1;
            }
        }

    }

}
