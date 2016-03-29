package com.example.adam.tunisia.View.Activities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class SCMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmpmap);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<Station> A = new ArrayList<Station>();

        A.add(new Station("Le Passage",new LatLng(36.806505, 10.180793)));
        A.add(new Station("Le Passage",new LatLng(36.810354, 10.172349)));
        A.add(new Station("Le Passage",new LatLng(36.813380, 10.168934)));
        A.add(new Station("Le Passage",new LatLng(36.809808, 10.162053)));
        A.add(new Station("Le Passage",new LatLng(36.808981, 10.148177)));
        A.add(new Station("Le Passage",new LatLng(36.808195, 10.143113)));
        A.add(new Station("Le Passage",new LatLng(36.807257, 10.135214)));
        A.add(new Station("Le Passage",new LatLng(36.805345, 10.126851)));
        A.add(new Station("Le Passage",new LatLng(36.802717, 10.115912)));
        A.add(new Station("Le Passage",new LatLng(36.802141, 10.102941)));



        Ligne L = new Ligne("Metro 4","Transtu",A);

        mapMapping(mMap, L);

        LatLng Center = new LatLng(36.859446, 10.277243);
        CameraPosition target = CameraPosition.builder().target(Center).zoom(14).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));

    }

    public void mapMapping(GoogleMap map,Ligne L){

        PolylineOptions P = new PolylineOptions();
        P.geodesic(true);

        for(int i=0;i<L.getA().size();i++){
            map.addMarker(new MarkerOptions().title(L.getA().get(i).getName()).snippet("Yes").position(L.getA().get(i).getLatLng()));
            P.add(L.getA().get(i).getLatLng());
        }


        P.width(10).color(Color.BLACK);

        map.addPolyline(P);

    }






}
