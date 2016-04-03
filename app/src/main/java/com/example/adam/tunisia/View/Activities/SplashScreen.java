package com.example.adam.tunisia.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.adam.tunisia.Model.Rest.RetrofitSociete;
import com.example.adam.tunisia.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashScreen extends Activity {

    boolean internet=false;

    //Executing internet check in a background thread
    class DownloadLink extends AsyncTask<Void, Void, Void> {

        boolean CONNECTED=false;

        @Override
        protected Void doInBackground(Void... params) {
            Log.v("BEFORE",CONNECTED+"");
            CONNECTED=isInternetWorking();
            Log.v("AFTER",CONNECTED+"");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.v("POST", CONNECTED + "");
            internet=CONNECTED;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreen.this, Home.class);
                    mainIntent.putExtra("internet",CONNECTED);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }

    }

    // Function checking if internet works
    public boolean isInternetWorking() {
        boolean success = false;
        try {
            URL url = new URL("https://google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            success = connection.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        RetrofitSociete RS = new RetrofitSociete(this);
        RS.getSocietes();

        new DownloadLink().execute();

        Log.v("Internet", internet + "");

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/




    }
}
