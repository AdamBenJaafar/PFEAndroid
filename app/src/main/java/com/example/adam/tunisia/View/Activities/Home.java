package com.example.adam.tunisia.View.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Entities.Actualite;
import com.example.adam.tunisia.Model.Entities.GooglePlaces.Example;
import com.example.adam.tunisia.Model.Entities.OpenWeather.Model;
import com.example.adam.tunisia.Model.Rest.GooglePlaces.GooglePlacesAPI;
import com.example.adam.tunisia.Model.Rest.OpenWeather.OpenWeatherAPI;
import com.example.adam.tunisia.Presenter.Helpers.GeoHelper;
import com.example.adam.tunisia.R;
import com.github.pwittchen.weathericonview.WeatherIconView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private List<Actualite> movieList = new ArrayList<>();


    @Bind(R.id.TVMeteo)
    TextView TVMeteo;

    @Bind(R.id.imageView)
    ImageView IV;

    @Bind(R.id.TVPlace)
    TextView TVPlace;

    @Bind(R.id.my_weather_icon)
    WeatherIconView weatherIconView;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




            //getPlaces();



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        ButterKnife.bind(this);


        weatherIconView.setIconResource(getString(R.string.wi_cloud));
        TVMeteo.setText("55 C°");

        if(!getIntent().getBooleanExtra("internet",true)){
            AlertDialog alertDialog = new AlertDialog.Builder(
                    this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Alerte");

            // Setting Dialog Message
            alertDialog.setMessage("Les donnes peuvent ne pas étre mises ajour");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.ic_warning_24dp);

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            // Showing Alert Message
            alertDialog.show();




        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //drawer.openDrawer(Gravity.LEFT);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        System.out.println(" OK GO BUILD ");

        //GeoHelper GH = new GeoHelper();
        //GH.BuildGraph(this);

    }

    public void showDialog(){
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.actualitedialog);
        dialog.setTitle("Actu");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Vous étes en train de vérifier la ligne ");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.mipmap.perturbation);


        Button dialogButtonB = (Button) dialog.findViewById(R.id.dialogButtonBACK);
        // if button is clicked, close the custom dialog
        dialogButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }



    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SplashScreen.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(this, Soceites.class);
            startActivity(i);
        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(this, MPActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, Localisation.class);
            startActivity(i);
        } /*else if (id == R.id.nav_share) {
            Intent i = new Intent(this, Soceites.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {
            Intent i = new Intent(this, Stations.class);
            startActivity(i);
        }*/ else if (id == R.id.nav_actualites) {
            Intent i = new Intent(this, Actualites.class);
            startActivity(i);
        } else if (id == R.id.nav_perturbations) {
            Intent i = new Intent(this, Perturbations.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop(){
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();

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

    void getReport(String lat, String lon) {

        String url = "http://api.openweathermap.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherAPI service = retrofit.create(OpenWeatherAPI.class);

        Call<Model> call = service.getWheatherReport(lat,lon);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                try {

                    String city = response.body().getName();
                    TVPlace.setText(city);

                    double temp = response.body().getMain().getTemp();
                    TVMeteo.setText(Math.round(temp) + " °c");

                    String icon = response.body().getWeather().get(0).getIcon();

                    switch(icon){
                        case"01d":  weatherIconView.setIconResource(getString(R.string.wi_day_sunny));  break;
                        case"01n":  weatherIconView.setIconResource(getString(R.string.wi_night_clear));  break;
                        case"02d":  weatherIconView.setIconResource(getString(R.string.wi_day_cloudy));  break;
                        case"02n":  weatherIconView.setIconResource(getString(R.string.wi_night_alt_cloudy));  break;
                        case"03d":  weatherIconView.setIconResource(getString(R.string.wi_cloud));  break;
                        case"03n":  weatherIconView.setIconResource(getString(R.string.wi_cloud));  break;
                        case"04d":  weatherIconView.setIconResource(getString(R.string.wi_cloudy));  break;
                        case"04n":  weatherIconView.setIconResource(getString(R.string.wi_cloudy));  break;
                        case"09d":  weatherIconView.setIconResource(getString(R.string.wi_hail));  break;
                        case"09n":  weatherIconView.setIconResource(getString(R.string.wi_hail));  break;
                        case"10d":  weatherIconView.setIconResource(getString(R.string.wi_day_showers));  break;
                        case"10n":  weatherIconView.setIconResource(getString(R.string.wi_night_alt_showers));  break;
                        case"11d":  weatherIconView.setIconResource(getString(R.string.wi_storm_showers));  break;
                        case"11n":  weatherIconView.setIconResource(getString(R.string.wi_storm_showers));  break;
                        case"13d":  weatherIconView.setIconResource(getString(R.string.wi_day_fog));  break;
                        case"13n":  weatherIconView.setIconResource(getString(R.string.wi_night_fog));  break;
                        default:  weatherIconView.setIconResource(getString(R.string.wi_refresh));  break;
                    }

                    Log.v("YES","WWEATHEROPTAINED");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("NOO","FAILED");
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                t.printStackTrace();
                Log.v("NOO","Onfailure");
            }


        });
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        getReport(location.getLatitude()+"",location.getLongitude()+"");
    }


    public void Open(View view){

        int id = view.getId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.textView7) {
            Intent i = new Intent(this, Soceites.class);
            startActivity(i);
        } else if (id == R.id.textView8) {
            Intent i = new Intent(this, MPActivity.class);
            startActivity(i);
        } else if (id == R.id.textView9) {
            Intent i = new Intent(this, Localisation.class);
            startActivity(i);
        } else if (id == R.id.textView10) {
            Intent i = new Intent(this, Actualites.class);
            startActivity(i);
        } else if (id == R.id.textView11) {
            Intent i = new Intent(this, Perturbations.class);
            startActivity(i);
        }



    }

}
