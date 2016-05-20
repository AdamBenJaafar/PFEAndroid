package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Model.Entities.Station_Ligne_Horaire;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterStation_Ligne_Horaire extends AdapterDB {

    public static final String TAG = "DBAdapterSLH";

    public static final String ROW_ID = "_id";
    public static final String HORAIRE = "horaire";

    public static final int COL_ROW_ID = 0;
    public static final int COL_HORAIRE = 1;

    String [] ALL_KEYS = { ROW_ID, HORAIRE };

    private static final String DATABASE_TABLE = "station_ligne_horaire";

    private SQLiteDatabase mDb;

     /* ***************************************************************
                        INITIALISATION METHODS
    *************************************************************** */

    public DBAdapterStation_Ligne_Horaire(Context ctx) {
        super(ctx);
    }

    public DBAdapterStation_Ligne_Horaire open() {
        super.open();
        return this;
    }

    @Override
    public void close() {
        super.close();
    }

     /* ***************************************************************
                         CRUD METHODS
    *************************************************************** */

    public long createStation_Ligne_Horaire(Station_Ligne_Horaire station_ligne_horaire){
        Log.v(TAG,"SLH Created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(ROW_ID, station_ligne_horaire.getSTATION_LIGNE().getROW_ID());
        initialValues.put(HORAIRE, station_ligne_horaire.getHORAIRE());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteStation_Ligne_Horaire(long rowId) {
        Log.v(TAG,"Station_Ligne_Horaire deleted");
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Station_Ligne_Horaire> getAllStation_Ligne_Horaire() {
        Log.v(TAG,"Station_Ligne_Horaire acquired");
        ArrayList<Station_Ligne_Horaire> A= new ArrayList<Station_Ligne_Horaire>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Station_Ligne_Horaire F= new Station_Ligne_Horaire();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String horaire = mCursor.getString(COL_HORAIRE);

                // Append data to the message:
                F.setSTATION_LIGNE(new Station_Ligne(id));
                F.setHORAIRE(horaire);

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public ArrayList<Station_Ligne_Horaire> getAllStation_Ligne_HoraireByStation_Ligne(int SL) {
        Log.v(TAG,"Station_Ligne_Horaire acquired");
        ArrayList<Station_Ligne_Horaire> A= new ArrayList<Station_Ligne_Horaire>();

        String where = ROW_ID + "=" + SL;

        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , where, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Station_Ligne_Horaire F= new Station_Ligne_Horaire();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String horaire = mCursor.getString(COL_HORAIRE);

                // Append data to the message:
                F.setSTATION_LIGNE(new Station_Ligne(id));
                F.setHORAIRE(horaire);

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Station_Ligne_Horaire getStation_Ligne_Horaire(long rowId) throws SQLException {
        Log.v(TAG,"Station_Ligne_Horaire acquired");
        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Station_Ligne_Horaire F= new Station_Ligne_Horaire();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String horaire = mCursor.getString(COL_HORAIRE);

                // Append data to the message:
                F.setSTATION_LIGNE(new Station_Ligne(id));
                F.setHORAIRE(horaire);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return F;
    }

    public void deleteAll() {
        Log.v(TAG,"Station_Ligne_Horaires deleted");
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

    public boolean updateStation_Ligne_Horaire(Station_Ligne_Horaire station_ligne_horaire){
        Log.v(TAG, "Station_Ligne_Horaire updated");
        String where = ROW_ID + "=" + station_ligne_horaire.getSTATION_LIGNE();

        ContentValues args = new ContentValues();
        args.put(ROW_ID, station_ligne_horaire.getSTATION_LIGNE().getROW_ID());
        args.put(HORAIRE, station_ligne_horaire.getHORAIRE());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }



}
