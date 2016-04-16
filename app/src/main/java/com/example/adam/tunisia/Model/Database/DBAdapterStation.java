package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.Model.Entities.Station;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterStation extends AdapterDB {

    public static final String TAG = "DBAdapterStation";

    public static final String ROW_ID = "_id";
    public static final String NOM = "nom";
    public static final String TYPE = "type";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String PRINCIPALE = "principale";
    public static final String SOC_ROW_ID = "soc_row_id";

    public static final int COL_ROW_ID = 0;
    public static final int COL_NOM = 1;
    public static final int COL_TYPE = 2;
    public static final int COL_LAT = 3;
    public static final int COL_LNG = 4;
    public static final int COL_PRINCIPALE = 5;
    public static final int COL_SOC_ROW_ID = 6;

    String [] ALL_KEYS = { ROW_ID, NOM, TYPE, LAT, LNG, PRINCIPALE, SOC_ROW_ID};

    private static final String DATABASE_TABLE = "station";

    private SQLiteDatabase mDb;

    // _____ CONSTRUCTOR, OPEN, CLOSE _____

    public DBAdapterStation(Context ctx) {
        super(ctx);
    }

    public DBAdapterStation open() {
        super.open();
        return this;
    }

    @Override
    public void close() {
        super.close();
    }

    // _____ CREATE, READ, UPDATE, DELETE _____

    public long createStation(Station station){
        Log.v(TAG, "Station created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(ROW_ID, station.getROW_ID());
        initialValues.put(NOM, station.getNOM());
        initialValues.put(TYPE, station.getTYPE());
        initialValues.put(LAT, station.getLATITUDE());
        initialValues.put(LNG, station.getLONGITUDE());
        initialValues.put(PRINCIPALE, ( station.isPRINCIPALE() ? 1 : 0 ) );
        initialValues.put(SOC_ROW_ID, station.getSOCIETE_ID());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteStation(long rowId) {
        Log.v(TAG,"Station deleted");
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }



    public ArrayList<Station> getAllStation() {
        Log.v(TAG,"Station acquired");
        ArrayList<Station> A= new ArrayList<Station>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Station S= new Station();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String nom = mCursor.getString(COL_NOM);
                String type = mCursor.getString(COL_TYPE);
                String lat = mCursor.getString(COL_LAT);
                String lng = mCursor.getString(COL_LNG);
                int principale = mCursor.getInt(COL_PRINCIPALE);
                String soc_row_id = mCursor.getString(COL_SOC_ROW_ID);

                // Append data to the message:
                S.setROW_ID(id);
                S.setNOM(nom);
                S.setTYPE(type);
                S.setLONGITUDE(lng);
                S.setLATITUDE(lat);
                S.setPRINCIPALE((principale == 1 ? true : false));
                S.setSOCIETE_ID(Long.parseLong(soc_row_id));

                A.add(S);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Station getStation(long rowId) {
        Log.v(TAG,"Station acquired");
        String where = ROW_ID + " = " + rowId;

        Cursor mCursor = this.db.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Station S= new Station();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String nom = mCursor.getString(COL_NOM);
                String type = mCursor.getString(COL_TYPE);
                String lat = mCursor.getString(COL_LAT);
                String lng = mCursor.getString(COL_LNG);
                int principale = mCursor.getInt(COL_PRINCIPALE);
                String soc_row_id = mCursor.getString(COL_SOC_ROW_ID);

                // Append data to the message:
                S.setROW_ID(id);
                S.setNOM(nom);
                S.setTYPE(type);
                S.setLONGITUDE(lng);
                S.setLATITUDE(lat);
                S.setPRINCIPALE((principale == 1 ? true : false));
                S.setSOCIETE_ID(Long.parseLong(soc_row_id));


            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return S;
    }

    public void deleteAll() {
        Log.v(TAG,"Stations deleted");
        Cursor c = this.db.query(DATABASE_TABLE, ALL_KEYS, null, null, null, null, null);
        long rowId = c.getColumnIndexOrThrow(ROW_ID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public boolean deleteRow(long rowId) {
        String where = ROW_ID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public boolean updateStation(Station station){
        Log.v(TAG,"Station updated");
        String where = ROW_ID + "=" + station.getROW_ID();

        ContentValues args = new ContentValues();
        args.put(NOM, station.getNOM());
        args.put(TYPE, station.getTYPE());
        args.put(LAT, station.getLATITUDE());
        args.put(LNG, station.getLONGITUDE());
        args.put(PRINCIPALE, station.isPRINCIPALE() ? 1 : 0 );
        args.put(SOC_ROW_ID, station.getSOCIETE_ID());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }

}