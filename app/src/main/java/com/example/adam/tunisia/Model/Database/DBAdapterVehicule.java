package com.example.adam.tunisia.Model.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;
import com.example.adam.tunisia.Model.Entities.Vehicule;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterVehicule extends AdapterDB {

    public static final String TAG = "DBAdapterVehicule";

    public static final String ROW_ID = "_id";
    public static final String LIGNE_ID = "ligne_id";
    public static final String IMMATRICULATION = "immatriculation";

    public static final int COL_ROW_ID = 0;
    public static final int COL_LIGNE_ID = 1;
    public static final int COL_IMMATRICULATION = 2;

    String[] ALL_KEYS = {ROW_ID, LIGNE_ID, IMMATRICULATION};

    private static final String DATABASE_TABLE = "vehicule";

    private SQLiteDatabase mDb;

     /* ***************************************************************
                        INITIALISATION METHODS
    *************************************************************** */

    public DBAdapterVehicule(Context ctx) {
        super(ctx);
    }

    public DBAdapterVehicule open() {
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

    public long createVehicule(Vehicule vehicule) {
        Log.v(TAG, "Vehicule created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(ROW_ID, vehicule.getROW_ID());
        initialValues.put(LIGNE_ID, vehicule.getLIGNE().getROW_ID());
        initialValues.put(IMMATRICULATION, vehicule.getIMMATRICULATION());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteVehicule(long rowId) {
        Log.v(TAG, "Vehicule deleted");
        String where = ROW_ID + "=" + rowId;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Vehicule> getAllVehiculeByLigne(int LigneID) {
        Log.v(TAG, "VehiculeByLigne acquired");
        ArrayList<Vehicule> A = new ArrayList<Vehicule>();

        String where = LIGNE_ID + "=" + LigneID;

        Cursor mCursor = this.db.query(DATABASE_TABLE, null, where, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Vehicule F = new Vehicule();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                int ligne_id = mCursor.getInt(COL_LIGNE_ID);
                String immatriculation = mCursor.getString(COL_IMMATRICULATION);

                // Append data to the message:
                F.setROW_ID(id);
                F.setLIGNE(new Ligne(ligne_id));
                F.setIMMATRICULATION(immatriculation);

                A.add(F);

            } while (mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public ArrayList<Vehicule> getAllVehicule() {
        Log.v(TAG, "Vehicule acquired");
        ArrayList<Vehicule> A = new ArrayList<Vehicule>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Vehicule F = new Vehicule();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                int ligne_id = mCursor.getInt(COL_LIGNE_ID);
                String immatriculation = mCursor.getString(COL_IMMATRICULATION);

                // Append data to the message:
                F.setROW_ID(id);
                F.setLIGNE(new Ligne(ligne_id));
                F.setIMMATRICULATION(immatriculation);

                A.add(F);

            } while (mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Vehicule getVehicule(long rowId) throws SQLException {
        Log.v(TAG, "Vehicule acquired");
        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, null, where, null, null, null, null, null);

        Vehicule F = new Vehicule();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                int ligne_id = mCursor.getInt(COL_LIGNE_ID);
                String immatriculation = mCursor.getString(COL_IMMATRICULATION);

                // Append data to the message:
                F.setROW_ID(id);
                F.setLIGNE(new Ligne(ligne_id));
                F.setIMMATRICULATION(immatriculation);

            } while (mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return F;
    }

    public void deleteAll() {
        Log.v(TAG, "Vehicules deleted");
        Cursor c = this.db.query(DATABASE_TABLE, null, null, null, null, null, null);
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

    public boolean updateVehicule(Vehicule vehicule) {
        Log.v(TAG, "Vehicule updated");
        String where = ROW_ID + "=" + vehicule.getROW_ID();

        ContentValues args = new ContentValues();
        args.put(LIGNE_ID, vehicule.getLIGNE().getROW_ID());
        args.put(IMMATRICULATION, vehicule.getIMMATRICULATION());

        return this.mDb.update(DATABASE_TABLE, args, where, null) > 0;
    }


}