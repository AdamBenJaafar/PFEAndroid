package com.example.adam.tunisia.View.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.R;
import com.example.adam.tunisia.View.Adapters.LignesAdapter;

import java.util.ArrayList;
import java.util.List;

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

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog();
            }
        });


        DBS.close();
    }

    public void showDialog(){
        List<String> mAnimals = new ArrayList<String>();
        mAnimals.add("18:40");
        mAnimals.add("19:40");
        mAnimals.add("20:40");
        //Create sequence of items
        final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Horaires");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

}

