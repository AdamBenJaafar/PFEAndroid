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
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Entities.GooglePlaces.Example;
import com.example.adam.tunisia.Model.Entities.GooglePlaces.Result;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Model.Rest.GooglePlaces.GooglePlacesAPI;
import com.example.adam.tunisia.Presenter.Helpers.GeoHelper;
import com.example.adam.tunisia.Presenter.Presenters.MPActivityPresenter;
import com.example.adam.tunisia.Presenter.Presenters.SCMapPresenter;
import com.example.adam.tunisia.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MPActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MPActivityPresenter Presenter;

    private HashMap<Integer,Integer> HM;
    int[] rainbow;

    boolean to,from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Réseau de transport");

        Presenter = new MPActivityPresenter(this);

        rainbow = this.getResources().getIntArray(R.array.rainbow);


        HM = new HashMap<Integer,Integer>();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        Presenter.drawNetwork();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
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

                DBAdapterStation DBAS = new DBAdapterStation(getBaseContext());
                DBAS.open();

                List<Station> LS = DBAS.getAllStation();

                DBAS.close();

                double dist = 1000000000;

                for( Station S : LS ){
                    Log.v("sssss","test");
                    if(GeoHelper.distFrom(Double.parseDouble(S.getLATITUDE()),Double.parseDouble(S.getLONGITUDE()),origin.latitude,origin.longitude)<dist){
                        dist = GeoHelper.distFrom(Double.parseDouble(S.getLATITUDE()),Double.parseDouble(S.getLONGITUDE()),origin.latitude,origin.longitude);
                        destination = new LatLng(Double.parseDouble(S.getLATITUDE()),Double.parseDouble(S.getLONGITUDE()));
                        Log.v("sssss","Smaller, changin");
                    }
                }

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

            }
        });

        getPlaces();

    }

    void getPlaces() {

        String url = "https://maps.googleapis.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GooglePlacesAPI service = retrofit.create(GooglePlacesAPI.class);

        Call<Example> call = service.getPlacesReport();

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                try {

                    for ( Result R : response.body().getResults()) {

                        double lat =    R.getGeometry().getLocation().getLat();
                        double lon =    R.getGeometry().getLocation().getLng();

                        LatLng LL = new LatLng(lat,lon);

                        CircleOptions circleOptions = new CircleOptions()
                                .center(LL)
                                .radius(20)
                                .strokeColor(Color.BLACK)
                                .strokeWidth(6);

                        mMap.addCircle(circleOptions);
                    }
                    Toast.makeText(getBaseContext(),response.body().getResults().get(0).getName(),Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("NOO","FAILED");
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

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
                LatLng POS = new LatLng(Double.parseDouble(SL.getSTATION().getLATITUDE()),Double.parseDouble(SL.getSTATION().getLONGITUDE()));

                MarkerOptions MO = new MarkerOptions().title(SL.getSTATION().getNOM()).position(POS).snippet("TEST").icon(BitmapDescriptorFactory.fromResource(R.mipmap.stat));
                mMap.addMarker(MO);
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
        P.width(4).color(color);



        // ADDING THE POLYLINE
        mMap.addPolyline(P);

    }

    public void drawNetwork(List<ArrayList<Station_Ligne>> Network,int col){

        for( int i = 0 ; i < Network.size() ; i++ ){

            ArrayList<Station_Ligne> L = Network.get(i);
            drawLine(L, rainbow[col%rainbow.length]);

        }

    }

    public void setCameraPosition(double lat, double lng, int zoom){
        CameraPosition target = CameraPosition.builder().target(new LatLng(lat,lng)).zoom(zoom).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    public void retour(View view){
        new LovelyStandardDialog(this)
                .setTopColor(ContextCompat.getColor(this,R.color.colorPrimary))
                .setButtonsColor(ContextCompat.getColor(this,R.color.colorAccent))
                .setIcon(R.mipmap.perturbation)
                .setTitle("")
                .setMessage("")
                .setPositiveButton("fromm", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to=false;
                        from=true;
                    }
                })
                .setNegativeButton("too", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to=true;
                        from=false;
                    }
                })
                .show();
    }


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
}
