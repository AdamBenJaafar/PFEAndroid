package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Presenter.Helpers.Geohelper;
import com.example.adam.tunisia.Presenter.Presenters.P_Localisation;
import com.example.adam.tunisia.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Localisation extends AppCompatActivity implements OnMapReadyCallback {

    // TAG
    private static final String TAG = "Localisation" ;

    // PRESENTER : P_Localisation
    P_Localisation P_Localisation;

    // GOOGLE MAP
    public GoogleMap mMap;


    /* *************************************************************
                        LIFECYCLE FUNCTIONS
    ****************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmap);

        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Localisation");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Presenter setup
        P_Localisation = new P_Localisation(this);
        P_Localisation.selectTracking();

    }

    @Override
    protected void onStop(){
        super.onStop();
        P_Localisation.onStop();
    }

    public void resetup(){
        mMap.clear();
       // P_Localisation.selectTracking();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    /* *************************************************************
                           MAP FUNCTIONS
    ****************************************************************/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(36.809182,10.148363);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }

    public void drawLine(ArrayList<Station_Ligne> ListeDesLignes){

        //CREATING THE POLYLINE
        PolylineOptions P = new PolylineOptions();
        P.geodesic(true);

        for(Station_Ligne SL : ListeDesLignes ) {


            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())))
                    .radius(20)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(6);


            if(SL.getSTATION().isPRINCIPALE()) {
              //  mMap.addMarker(new MarkerOptions().title(SL.getSTATION().getNOM())
              //          .snippet(SL.getSTATION().getNOM())
              //          .position(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE()))));
                circleOptions.radius(20).fillColor(Color.BLACK).strokeColor(Color.BLACK);
            }

            DBAdapterStation DBAS = new DBAdapterStation(this);
            DBAS.open();

            Station S = DBAS.getStation(SL.getSTATION().getROW_ID());

            DBAS.close();

            LatLng POS = new LatLng(Double.parseDouble(S.getLATITUDE()),Double.parseDouble(S.getLONGITUDE()));

            MarkerOptions MO = new MarkerOptions().title(S.getNOM()).position(POS).icon(BitmapDescriptorFactory.fromResource(R.mipmap.stat));
           // mMap.addMarker(MO);

            P.add(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())));
            mMap.addCircle(circleOptions);
        }
        P.width(4).color(Color.GRAY);



        // ADDING THE POLYLINE
        mMap.addPolyline(P);

    }

    public void drawNetworkOfLine(String Ligne, String Soc){

        // CAMERA SETTING VARIABLES
        // -181 & +181 are the bounds of the latlng
        double MaxLNG = -181.0;
        double MinLNG = 181;
        double MaxLAT = -181.0;
        double MinLAT = 181.0;
        int Zoom;


        DBAdapterLigne myDBLigne;
        DBAdapterStation_Ligne myDBStation_Ligne;
        DBAdapterStation myDBStation;

        myDBLigne = new DBAdapterLigne(this);
        myDBStation_Ligne = new DBAdapterStation_Ligne(this);
        myDBStation = new DBAdapterStation(this);

        myDBStation.open();
        myDBStation_Ligne.open();
        myDBLigne.open();



        try {

            List<ArrayList<Station_Ligne>> Network = new ArrayList<ArrayList<Station_Ligne>>();

            // GET LINES
            List<Ligne> LL = myDBLigne.getAllLigneBySocieteAller(Soc);
            // FILL LINES
            for( Ligne L : LL) {
                Log.v("We are checking", Ligne + " and the currsent is " + L.getIDENTIFIANT());
                if(L.getIDENTIFIANT().equals(Ligne)) {
                    // GET STATIONS
                    ArrayList<Station_Ligne> SLL = myDBStation_Ligne.getAllStation_LigneByLigne(L.getROW_ID());
                    // FILL STATIONS
                    for (Station_Ligne SL : SLL) {

                        SL.setSTATION(myDBStation.getStation(SL.getSTATION().getROW_ID()));

                        if (Double.parseDouble(SL.getSTATION().getLATITUDE()) > MaxLAT) {
                            MaxLAT = Double.parseDouble(SL.getSTATION().getLATITUDE());
                        }
                        if (Double.parseDouble(SL.getSTATION().getLATITUDE()) < MinLAT) {
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

                    drawLine(SLL);
                }
             //   Network.add(SLL);

            }

           // SCMap.drawNetwork(Network);

            myDBStation_Ligne.close();
            myDBStation.close();
            myDBLigne.close();

        }catch(Exception e){
            //Log.v(TAG,"An exception has been thrown");
            e.printStackTrace();
        }

        Log.v(TAG," MAXLAT = "+ MaxLAT);
        Log.v(TAG," MINLAT = "+ MinLAT);
        Log.v(TAG," MAXLNG = "+ MaxLNG);
        Log.v(TAG," MINLNG = "+ MinLNG);
        Zoom = Geohelper.getZoomLevel(Math.max(Geohelper.distFrom(MaxLAT,0,MinLAT,0),Geohelper.distFrom(MaxLNG,0,MinLNG,0)));


        // SETTING CAMERA POSITION
       // setCameraPosition((MaxLAT+MinLAT)/2,(MaxLNG+MinLNG)/2, Zoom);


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng((MaxLAT+MinLAT)/2,(MaxLNG+MinLNG)/2))
                .zoom(Zoom)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        Log.v(TAG, " zoom = " + Zoom);

    }


    public void setCameraPosition(double lat, double lng, int zoom){
        CameraPosition target = CameraPosition.builder().target(new LatLng(lat,lng)).zoom(zoom).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    public void animateMarkerTo(final Marker marker, final double lat, final double lng) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long DURATION_MS = 3000;
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final LatLng startPosition = marker.getPosition();
        handler.post(new Runnable() {
            @Override
            public void run() {
                float elapsed = SystemClock.uptimeMillis() - start;
                float t = elapsed/DURATION_MS;
                float v = interpolator.getInterpolation(t);

                double currentLat = (lat - startPosition.latitude) * v + startPosition.latitude;
                double currentLng = (lng - startPosition.longitude) * v + startPosition.longitude;
                marker.setPosition(new LatLng(currentLat, currentLng));

                // if animation is not finished yet, repeat
                if (t < 1) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    public void animateCamera(LatLng LatLng){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(LatLng)
                .zoom(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        CircleOptions circleOptions = new CircleOptions()
//                .center(LatLng)
//                .radius(20)
//                .strokeColor(Color.BLACK)
//                .strokeWidth(6);
//        mMap.addCircle(circleOptions);
    }

    public void goToPosition(View view){
        P_Localisation.showDialog();
    }


    public void retour(){
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    /* *************************************************************
                        ACTIVITY FUNCTIONS
    *************************************************************** */

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

    public void addMarker(MarkerOptions MarkerOptions) {
        mMap.addMarker(MarkerOptions);
    }

}
