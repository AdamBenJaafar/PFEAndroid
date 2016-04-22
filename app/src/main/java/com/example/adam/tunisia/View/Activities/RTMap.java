package com.example.adam.tunisia.View.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Main2Activity;
import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Presenter.Presenters.RTMapPresenter;
import com.example.adam.tunisia.R;
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

public class RTMap extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "RTMap" ;

    RTMapPresenter RTMapPresenter;

    public GoogleMap mMap;

    private MarkerOptions position;

    public Marker X;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmap);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        LatLng sydney = new LatLng(36.809182,10.148363);
        position = new MarkerOptions().position(sydney).title("Marker in Sydney");

        RTMapPresenter = new RTMapPresenter(this);
        RTMapPresenter.selectSociete();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng sydney = new LatLng(36.809182,10.148363);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

         X =   this.mMap.addMarker(new MarkerOptions().position(new LatLng(36.809182,10.148363)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.tram)));

    }

    @Override
    protected void onStop(){
        super.onStop();
        RTMapPresenter.onStop();
    }

    @Override
    protected void onStart(){
        super.onStart();


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



            P.add(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())));
            mMap.addCircle(circleOptions);
        }
        P.width(4).color(Color.GRAY);



        // ADDING THE POLYLINE
        mMap.addPolyline(P);

    }

    public void drawNetworkOfLine(String Ligne){

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
            List<Ligne> LL = myDBLigne.getAllLigneBySocieteAller("id");
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
///////////////
      //  Zoom = GeoHelper.getZoomLevel(Math.max(GeoHelper.distFrom(MaxLAT,0,MinLAT,0),GeoHelper.distFrom(MaxLNG,0,MinLNG,0)));

        // SETTING CAMERA POSITION
       //////////////////////////////
        // SCMap.setCameraPosition((MaxLAT+MinLAT)/2,(MaxLNG+MinLNG)/2, Zoom);
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

    public void addMarker(MarkerOptions MarkerOptions) {
        mMap.addMarker(MarkerOptions);
    }

    public void animateCamera(LatLng LatLng){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(LatLng)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        CircleOptions circleOptions = new CircleOptions()
                .center(LatLng)
                .radius(20)
                .strokeColor(Color.BLACK)
                .strokeWidth(6);
        mMap.addCircle(circleOptions);
    }

    public void goToPosition(View view){

        RTMapPresenter.showDialog();

    }


        public void retour(){
            Intent i = new Intent(this, Home.class);
            startActivity(i);
        }

}
