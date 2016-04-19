package com.example.adam.tunisia.View.Activities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Presenter.Helpers.GeoHelper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SCMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SCMapPresenter Presenter;

    private HashMap<Integer,Integer> HM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmpmap);

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

        String serverKey = "AIzaSyC8jV2zXq-4-4h3wbAzb-a04tyB1jueZP0";
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
                        Log.v("TETSETS",pointList.toString());
                        PolylineOptions polylineOptions = DirectionConverter.createPolyline(getBaseContext(), pointList, 5, Color.RED);
                        mMap.addPolyline(polylineOptions);
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Log.v("TESTTEST", " FAILED");
                    }
                });


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
                mMap.addMarker(new MarkerOptions().title(SL.getSTATION().getNOM())
                        .snippet(SL.getSTATION().getNOM())
                        .position(new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()), Double.parseDouble(SL.getSTATION().getLONGITUDE())))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
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

            drawLine(L, GeoHelper.Colors[Network.indexOf(L)%GeoHelper.Colors.length]);
        
        }

    }

    public void setCameraPosition(double lat, double lng, int zoom){
        CameraPosition target = CameraPosition.builder().target(new LatLng(lat,lng)).zoom(zoom).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }



}
