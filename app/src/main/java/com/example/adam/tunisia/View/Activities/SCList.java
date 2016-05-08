package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.adam.tunisia.Model.Database.DBAdapterSociete;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class SCList extends AppCompatActivity {

    public final static String ID_EXTRA="com.example.adam.tunisia.View.Activities.SCList._ID";

    ListView vue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmplist);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedCurrentDate = sdf.parse("2013-09-18");
            String date = sdf.format(convertedCurrentDate);

            Toast.makeText(this, date.toString(), Toast.LENGTH_LONG).show();
            Log.v("TEST","SUCCES");
        }catch(Exception e){
            Log.v("TEST","FAIL");
        }


        // MENU
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Liste des sociétés");
        setSupportActionBar(toolbar);

        // TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Liste des sociétés");

        // FAB Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DBAdapterSociete DBAS = new DBAdapterSociete(this);
        DBAS.open();
        final List<Societe> LS = DBAS.getAllSociete();
        DBAS.close();

        // LISTING
        vue = (ListView) findViewById(R.id.listView);


        List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> element;
        for(int i = 0 ; i < LS.size() ; i++) {
            element = new HashMap<String, String>();
            element.put("text1", LS.get(i).getIDENTIFICATEUR());
            element.put("text2", LS.get(i).getNOMCOMPLET());
            liste.add(element);
        }

        ListAdapter adapter = new SimpleAdapter(this,
                liste,
                android.R.layout.simple_list_item_2,
                new String[] {"text1", "text2"},
                new int[] {android.R.id.text1, android.R.id.text2 });

        vue.setAdapter(adapter);

        vue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SCList.this, SCDetails.class)   ;
                i.putExtra(ID_EXTRA,LS.get((int)id).getIDENTIFICATEUR());
                i.putExtra("SocID",LS.get((int)id).getROW_ID());
                //////////// implements serializable to pass a class between two actvities or juste some informations

                startActivity(i);
            }
        });

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
