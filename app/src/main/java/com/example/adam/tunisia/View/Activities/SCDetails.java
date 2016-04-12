package com.example.adam.tunisia.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.adam.tunisia.R;

public class SCDetails extends AppCompatActivity {

    TextView Text;
    String Company;

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


        Company = getIntent().getStringExtra(SCList.ID_EXTRA);

    }

    public void goToFeedback(View view){
        Intent i = new Intent(getApplicationContext(), SCFeedback.class);
        i.putExtra("Company",Company);
        startActivity(i);
    }

    public void goToMap(View view){
        Intent i = new Intent(getApplicationContext(), SCMap.class);
        i.putExtra("Company",Company);
        startActivity(i);
    }


}
