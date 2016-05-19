package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Adapters.LigneRVAdapter;
import com.example.adam.tunisia.View.Adapters.PerturbationRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SCDetails extends AppCompatActivity {

    @Bind(R.id.TVDetails)
    TextView TVDetails;
    @Bind(R.id.TVNom)
    TextView TVNom_Complet;
    @Bind(R.id.TVSiege)
    TextView TVSiege;
    @Bind(R.id.TVSite)
    TextView TVSite;
    @Bind(R.id.TVFormeJuridique)
    TextView TVFormeJuridique;

    @Bind(R.id.RVLignes)
    RecyclerView RV;

    LigneRVAdapter Adapter;

    String Company;
    long SocID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmpdetails);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Company = getIntent().getStringExtra("SocIDEN");
        SocID = getIntent().getLongExtra("SocID",0);

        Societe S = new Societe();

        try {
            DBAdapterSociete DBAS = new DBAdapterSociete(this);
            DBAS.open();
            S = DBAS.getSociete(SocID);
            DBAS.close();
        }catch (Exception e){

        }


        DBAdapterLigne DBAL = new DBAdapterLigne(this);
        DBAL.open();
        List<Ligne> LL = DBAL.getAllLigneBySocieteAller(S.getIDENTIFICATEUR());
        DBAL.close();

        boolean train = false;
        boolean metro = false;
        boolean bus = false;
        ButterKnife.bind(this);
        Adapter = new LigneRVAdapter(this, LL);

        RV.setAdapter(Adapter);
        RV.setLayoutManager( new LinearLayoutManager(this) );



        TVDetails.setText(S.getDETAILS());
        TVFormeJuridique.setText(S.getFORMEJURIDIQUE());
        TVNom_Complet.setText(S.getNOMCOMPLET());
        TVSiege.setText(S.getSIEGESOCIAL());
        TVSite.setText(S.getSITEWEB());

       // Toast.makeText(SCDetails.this, S.toString() , Toast.LENGTH_LONG).show();

        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Company);



       // ImageView IV = (ImageView ) findViewById(R.id.imageView5);
        ImageView IVC = (ImageView ) findViewById(R.id.profile_image);


        DBAdapterSociete DBAS = new DBAdapterSociete(this);
        DBAS.open();

        ArrayList<Societe> LS = DBAS.getAllSociete();
        try {


            byte[] blob = Base64.decode(S.getLOGO(), Base64.DEFAULT);

            System.out.println(LS.get(0).getLOGO());
            System.out.println(LS.get(0).getLOGO().substring(LS.get(0).getLOGO().length() - 10, LS.get(0).getLOGO().length()));

            //  String X = LS.get(0).getLogo();
            //  Log.v("TeSTADDD AE", X.substring(X.length()-10,X.length()));
            //  Log.v("TEST",blob+"");

            Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);


           // IV.setImageBitmap(Bitmap.createScaledBitmap(bmp, 300, 300, false));

            IVC.setImageBitmap(Bitmap.createScaledBitmap(bmp, 300, 300, false));

         // IV.setImageBitmap(bmp);

           // IV.setMinimumWidth(500);
           // IV.setMinimumHeight(500);



            //Toast.makeText(this,LS.get(0).toString(),Toast.LENGTH_LONG).show();

            DBAS.close();


        }catch(Exception e){

        }

    }

    public void goToFeedback(View view){
        Intent i = new Intent(getApplicationContext(), SCFeedback.class);
        i.putExtra("Societe",Company);
        startActivity(i);
    }

    public void goToMap(View view){
        Intent i = new Intent(getApplicationContext(), SCMap.class);
        i.putExtra("Company",Company);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Soceites.class);
                intent.putExtra("Societe",Company);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
