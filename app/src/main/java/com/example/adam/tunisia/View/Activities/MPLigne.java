package com.example.adam.tunisia.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Adapters.LignesAdapter;

public class MPLigne extends AppCompatActivity {

    ListView LV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpligne);

        DBAdapterStation DBS = new DBAdapterStation(this);
        DBS.open();

        Log.v("ggg",DBS.getAllStation().toString());

        LV = (ListView) findViewById(R.id.listView3);
        LV.setAdapter(new LignesAdapter(DBS.getAllStation(),this    ));

        DBS.close();
    }
}

