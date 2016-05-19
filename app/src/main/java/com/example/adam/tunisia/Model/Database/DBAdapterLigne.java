package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterLigne extends AdapterDB {

    public static final String TAG = "DBAdapterLigne";

    public static final String ROW_ID = "_id";
    public static final String DIRECTION = "direction";
    public static final String IDENTIFIANT = "identifiant";
    public static final String TYPE = "type";
    public static final String SOC_ROW_ID = "soc_row_id";

    public static final int COL_ROW_ID = 0;
    public static final int COL_DIRECTION = 1;
    public static final int COL_IDENTIFIANT = 2;
    public static final int COL_TYPE = 3;
    public static final int COL_SOC_ROW_ID = 4;

    String [] ALL_KEYS = { ROW_ID, DIRECTION, IDENTIFIANT, TYPE, SOC_ROW_ID};

    private static final String DATABASE_TABLE = "ligne";

    private SQLiteDatabase mDb;

     /* ***************************************************************
                        INITIALISATION METHODS
    *************************************************************** */

    public DBAdapterLigne(Context ctx) {
        super(ctx);
    }

    public DBAdapterLigne open() {
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

    public long createLigne(Ligne ligne){
        Log.v(TAG, "Ligne created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(ROW_ID, ligne.getROW_ID());
        initialValues.put(DIRECTION, ligne.getDIRECTION());
        initialValues.put(IDENTIFIANT, ligne.getIDENTIFIANT());
        initialValues.put(TYPE, ligne.getTYPE());
        initialValues.put(SOC_ROW_ID, ligne.getSOC().getIDENTIFICATEUR());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteLigne(long rowId) {
        Log.v(TAG, "Ligne deleted");
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Ligne> getAllLigne() {
        Log.v(TAG,"Ligne acquired");
        ArrayList<Ligne> A= new ArrayList<Ligne>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Ligne L= new Ligne();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String direction = mCursor.getString(COL_DIRECTION);
                String identifiant = mCursor.getString(COL_IDENTIFIANT);
                String type = mCursor.getString(COL_TYPE);
                String soc_row_id = mCursor.getString(COL_SOC_ROW_ID);

                // Append data to the message:
                L.setROW_ID(id);
                L.setDIRECTION(direction);
                L.setIDENTIFIANT(identifiant);
                L.setTYPE(type);
                L.setSOC(new Societe(soc_row_id));

                A.add(L);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public ArrayList<Ligne> getAllLigneBySocieteAller(String SocieteIDENTIFIANT) {
        Log.v(TAG,"Lignes by societe  acquired");
        ArrayList<Ligne> A= new ArrayList<Ligne>();

        String where = SOC_ROW_ID + " LIKE '%"  + SocieteIDENTIFIANT + "%' and "+ DIRECTION + " LIKE '%aller%'";

        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , where, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Ligne L= new Ligne();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String direction = mCursor.getString(COL_DIRECTION);
                String identifiant = mCursor.getString(COL_IDENTIFIANT);
                String type = mCursor.getString(COL_TYPE);
                String soc_row_id = mCursor.getString(COL_SOC_ROW_ID);

                // Append data to the message:
                L.setROW_ID(id);
                L.setDIRECTION(direction);
                L.setIDENTIFIANT(identifiant);
                L.setTYPE(type);
                L.setSOC(new Societe(soc_row_id));

                A.add(L);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Ligne getLigne(long rowId){

        String where = ROW_ID + " = " + rowId;

        Cursor mCursor = this.db.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Ligne L= new Ligne();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String direction = mCursor.getString(COL_DIRECTION);
                String identifiant = mCursor.getString(COL_IDENTIFIANT);
                String type = mCursor.getString(COL_TYPE);
                String soc_row_id = mCursor.getString(COL_SOC_ROW_ID);

                // Append data to the message:
                L.setROW_ID(id);
                L.setDIRECTION(direction);
                L.setIDENTIFIANT(identifiant);
                L.setTYPE(type);
                L.setSOC(new Societe(soc_row_id));

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();


        Log.v(TAG,"getLigne SUCCEEDED");
        return L;
    }

    public Ligne getLigne(String ID){

        String where = IDENTIFIANT + " LIKE '%"  + ID + "%' and "+ DIRECTION + " LIKE '%aller%'";;

        Cursor mCursor = this.db.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Ligne L= new Ligne();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String direction = mCursor.getString(COL_DIRECTION);
                String identifiant = mCursor.getString(COL_IDENTIFIANT);
                String type = mCursor.getString(COL_TYPE);
                String soc_row_id = mCursor.getString(COL_SOC_ROW_ID);

                // Append data to the message:
                L.setROW_ID(id);
                L.setDIRECTION(direction);
                L.setIDENTIFIANT(identifiant);
                L.setTYPE(type);
                L.setSOC(new Societe(soc_row_id));

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();


        Log.v(TAG,"getLigne SUCCEEDED");
        return L;
    }

    public void deleteAll() {
        Log.v(TAG,"Lignes deleted");
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

    public boolean updateLigne(Ligne ligne){
        Log.v(TAG, "Ligne updated");
        String where = ROW_ID + "=" + ligne.getROW_ID();

        ContentValues args = new ContentValues();
        args.put(DIRECTION, ligne.getDIRECTION());
        args.put(IDENTIFIANT, ligne.getIDENTIFIANT());
        args.put(TYPE, ligne.getTYPE());
        args.put(SOC_ROW_ID, ligne.getSOC().getIDENTIFICATEUR());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }

}