package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Presenter.Helpers.Geohelper;
import com.example.adam.tunisia.Presenter.Presenters.SCMapPresenter;
import com.example.adam.tunisia.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SCMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SCMapPresenter Presenter;

    private HashMap<Integer,Integer> HM;

    public String Societe;
    long SocID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmpmap);


        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Réseau de transport");

        Societe = getIntent().getStringExtra("Societe");
        SocID = getIntent().getLongExtra("SocID",0);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Presenter = new SCMapPresenter(this);

        HM = new HashMap<Integer,Integer>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        Presenter.drawNetwork();

       /* String serverKey = "AIzaSyC8jV2zXq-4-4h3wbAzb-a04tyB1jueZP0";
        LatLng origin = new LatLng(36.809182,10.148363);
        LatLng destination = new LatLng(36.809182, 10.185);
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.WALKING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        Route route = direction.getRouteList().get(0);
                        Leg leg = route.getLegList().get(0);
                        ArrayList<LatLng> pointList = leg.getDirectionPoint();
                        PolylineOptions polylineOptions = DirectionConverter.createPolyline(getBaseContext(), pointList, 5, Color.RED);
                        mMap.addPolyline(polylineOptions);
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Log.v("TESTTEST", " FAILED");
                    }
                });
*/


     /*   mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);


                String serverKey = "AIzaSyC8jV2zXq-4-4h3wbAzb-a04tyB1jueZP0";
                LatLng origin = new LatLng(latLng.latitude,latLng.longitude);
                LatLng destination = new LatLng(36.809182, 10.185);

                /*GoogleDirection.withServerKey(serverKey)
                        .from(origin)
                        .to(destination)
                        .transportMode(TransportMode.WALKING)
                        .execute(new DirectionCallback() {
                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                Route route = direction.getRouteList().get(0);
                                Leg leg = route.getLegList().get(0);
                                ArrayList<LatLng> pointList = leg.getDirectionPoint();
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(getBaseContext(), pointList, 5, Color.RED);
                                mMap.addPolyline(polylineOptions);
                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {
                                Log.v("TESTTEST", " FAILED");
                            }
                        });

            }
        });*/


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, SCDetails.class);
                intent.putExtra("SocIDEN",Societe);
                intent.putExtra("SocID",SocID);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void drawLine(ArrayList<Station_Ligne> ListeDesLignes, int color ){



        //CREATING THE POLYLINE
        PolylineOptions P = new PolylineOptions();
        P.geodesic(true);

        for(Station_Ligne SL : ListeDesLignes ) {


            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())))
                    .radius(20)
                    .strokeColor(color)
                    .strokeWidth(6);


            if(SL.getSTATION().isPRINCIPALE()) {
                /*mMap.addMarker(new MarkerOptions().title(SL.getSTATION().getNOM())
                        .snippet(SL.getSTATION().getNOM())
                        .position(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/
                circleOptions.radius(20).fillColor(color).strokeColor(color);
            }


            if(!this.HM.containsKey(SL.getSTATION().getROW_ID())){
                this.HM.put(SL.getSTATION().getROW_ID(), 1);
                Log.v("Hashmap",SL.getSTATION().getROW_ID()+"  ADDED  ");
            }else {
                this.HM.put(SL.getSTATION().getROW_ID(), ((int) HM.get(SL.getSTATION().getROW_ID()) + 1));
                circleOptions.radius(20+(10*HM.get(SL.getSTATION().getROW_ID())));
                circleOptions.strokeWidth(3);
                Log.v("Hashmap",SL.getSTATION().getROW_ID()+"  FOUND");
            }

            P.add(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())));
            mMap.addCircle(circleOptions);
        }
        P.width(4).color(Color.GRAY);



        // ADDING THE POLYLINE
        mMap.addPolyline(P);

    }

    public void drawNetwork(List<ArrayList<Station_Ligne>> Network){

        for( ArrayList<Station_Ligne> L : Network ){

            drawLine(L, Geohelper.Colors[Network.indexOf(L)%Geohelper.Colors.length]);
        
        }

    }

    public void setCameraPosition(double lat, double lng, int zoom){
        CameraPosition target = CameraPosition.builder().target(new LatLng(lat,lng)).zoom(zoom).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    public void retour(View view){
        Intent intent = new Intent(this, SCDetails.class);
        startActivity(intent);
    }



}
