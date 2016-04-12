package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.adam.tunisia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SCList extends AppCompatActivity {

    public final static String ID_EXTRA="com.example.adam.tunisia.View.Activities.SCList._ID";

    ListView vue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmplist);

        // MENU
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Liste de comjkjjjpagnies");
        setSupportActionBar(toolbar);

        // FAB Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // LISTING
        vue = (ListView) findViewById(R.id.listView);

        String[][] repertoire = new String[][]{
                {"TRANSTU", "Transtu"},
                {"SNTRI", "Société Nationale de Transport Inter urbain"},
                {"SRTGN", "Société Régionale de Transport du Gouvernorat de Nabeul"},
                {"SRTB", "Société Régionale de Transport de Bizerte"}};


        List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> element;
        for(int i = 0 ; i < repertoire.length ; i++) {
            element = new HashMap<String, String>();
            element.put("text1", repertoire[i][0]);
            element.put("text2", repertoire[i][1]);
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
                i.putExtra(ID_EXTRA,String.valueOf(id));
                //////////// implements serializable to pass a class between two actvities or juste some informations

                startActivity(i);
            }
        });

    }

}
