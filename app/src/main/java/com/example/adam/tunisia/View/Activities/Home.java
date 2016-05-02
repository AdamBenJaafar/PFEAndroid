package com.example.adam.tunisia.View.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
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
import android.widget.Toast;

import com.example.adam.tunisia.Main2Activity;
import com.example.adam.tunisia.Model.Database.DBAdapterActualite;
import com.example.adam.tunisia.Model.Database.DBAdapterPerturbation;
import com.example.adam.tunisia.Model.Entities.Actualite;
import com.example.adam.tunisia.Model.Entities.GooglePlaces.Example;
import com.example.adam.tunisia.Model.Entities.OpenWeather.Model;
import com.example.adam.tunisia.Model.Entities.Perturbation;
import com.example.adam.tunisia.Model.Rest.GooglePlaces.GooglePlacesAPI;
import com.example.adam.tunisia.Model.Rest.Weather.OpenWeatherAPI;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Adapters.ActualitesAdapter;
import com.example.adam.tunisia.View.Adapters.DividerItemDecorations;
import com.github.pwittchen.weathericonview.WeatherIconView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private RecyclerView recyclerView;
    private ActualitesAdapter mAdapter;



    @Bind(R.id.TEST)
    TextView TT ;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ActualitesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecorations(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showDialog();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareActualiteData();

      /*  new LovelyInfoDialog(this)
                .setTopColor(color(R.color.darkBlueGrey))
                .setIcon(R.mipmap.perturbation)
                //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                .setNotShowAgainOptionEnabled(0)
                .setTitle("TESTESTESTESTE")
                .setMessage("TESTESTESTESTE")
                .show();*/

            new LovelyStandardDialog(this)
                    .setTopColor(color(R.color.colorPrimaryDark))
                    .setButtonsColor(color(R.color.colorAccent))
                    .setIcon(R.mipmap.perturbation)
                    .setTitle("Bloquage du metro X4 au niveau de la station le Passage")
                    .setMessage("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
            //getPlaces();


        getReport();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        ButterKnife.bind(this);

        TT.setText("111111");

        if(!getIntent().getBooleanExtra("internet",false)){
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
        drawer.openDrawer(Gravity.LEFT);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        WeatherIconView weatherIconView;
        weatherIconView = (WeatherIconView) findViewById(R.id.my_weather_icon);
        weatherIconView.setIconResource(getString(R.string.wi_cloud));

    }

    private int color(int colorRes) {
        return ContextCompat.getColor(this, colorRes);
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

    private void prepareActualiteData() {
        Actualite movie = new Actualite("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        DBAdapterActualite DBA = new DBAdapterActualite(this);
        DBA.open();


        for( Actualite A : DBA.getAllActualite() ){
            movieList.add(A);
        }
        DBA.close();


        DBAdapterPerturbation DBP = new DBAdapterPerturbation(this);
        DBP.open();


        for( Perturbation A : DBP.getAllPerturbation() ){
         //   movieList.add(new Actualite(A.getLIG().getIDENTIFIANT()));
        }
        DBP.close();


        movie = new Actualite("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Home.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Home.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
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
            Intent i = new Intent(this, SCList.class);
            startActivity(i);
        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(this, MPActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, Main2Activity.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            Intent i = new Intent(this, RTMap.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {
            Intent i = new Intent(this, MPLigne.class);
            startActivity(i);
        } else if (id == R.id.nav_actualites) {
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


                    String city = response.body().getResults().get(0).getName();

                    String status = response.body().getResults().get(0).getGeometry().toString();

                    String humidity = response.body().getResults().get(0).getReference();

                    String pressure = response.body().getResults().get(0).getTypes().toString();

                    String message = city +"  " + status +"  " + humidity +"  " + pressure;

                    Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
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

    void getReport() {

        String url = "http://api.openweathermap.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherAPI service = retrofit.create(OpenWeatherAPI.class);

        Call<Model> call = service.getWheatherReport();

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                try {


                    String city = response.body().getName();

                    String status = response.body().getWeather().get(0).getDescription();

                    String humidity = response.body().getMain().getHumidity().toString();

                    String pressure = response.body().getMain().getPressure().toString();

                    String message = city +"  " + status +"  " + humidity +"  " + pressure;

                    Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
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
        TT.setText(location.toString());
    }
}
