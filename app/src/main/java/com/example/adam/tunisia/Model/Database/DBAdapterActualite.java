package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Actualite;
import com.example.adam.tunisia.Model.Entities.Societe;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterActualite extends AdapterDB {

    public static final String TAG = "DBAdapterActualite";

    public static final String ROW_ID = "_id";
    public static final String TEXTE = "texte";
    public static final String DATE = "date";
    public static final String SOC_ROW_ID = "soc_row_id";

    public static final int COL_ROW_ID = 0;
    public static final int COL_TEXTE = 1;
    public static final int COL_DATE = 2;
    public static final int COL_SOC_ROW_ID = 3;

    String [] ALL_KEYS = { ROW_ID, TEXTE, DATE,SOC_ROW_ID};

    private static final String DATABASE_TABLE = "actualite";

    private SQLiteDatabase mDb;

    // _____ CONSTRUCTOR, OPEN, CLOSE _____

    public DBAdapterActualite(Context ctx) {
        super(ctx);
    }

    public DBAdapterActualite open() {
        super.open();
        return this;
    }

    @Override
    public void close() {
        super.close();
    }

    // _____ CREATE, READ, UPDATE, DELETE _____

    public long createActualite(Actualite actualite){
        Log.v(TAG, "Actualite created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(DATE, actualite.getDATE());
        initialValues.put(TEXTE, actualite.getTEXTE());
        initialValues.put(SOC_ROW_ID, actualite.getSOC().getIDENTIFICATEUR());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteActualite(long rowId) {
        Log.v(TAG,"Actualite deleted");
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Actualite> getAllActualite() {
        Log.v(TAG,"Actualite acquired");
        ArrayList<Actualite> A= new ArrayList<Actualite>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Actualite F= new Actualite();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String texte = mCursor.getString(COL_TEXTE);
                String soc_row_id = mCursor.getString(COL_SOC_ROW_ID);

                // Append data to the message:
                F.setROW_ID(id);
                F.setDATE(date);
                F.setTEXTE(texte);
                F.setSOC(new Societe(soc_row_id));

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Actualite getActualite(long rowId) throws SQLException {
        Log.v(TAG,"Actualite acquired");
        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Actualite F= new Actualite();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String texte = mCursor.getString(COL_TEXTE);
                String soc_row_id = mCursor.getString(COL_SOC_ROW_ID);

                // Append data to the message:
                F.setROW_ID(id);
                F.setDATE(date);
                F.setTEXTE(texte);
                F.setSOC(new Societe(soc_row_id));

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return F;
    }

    public void deleteAll() {
        Log.v(TAG,"Actualites deleted");
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

    public boolean updateActualite(Actualite actualite){
        Log.v(TAG,"Actualite updated");
        String where = ROW_ID + "=" + actualite.getROW_ID();

        ContentValues args = new ContentValues();
        args.put(DATE, actualite.getDATE());
        args.put(TEXTE, actualite.getTEXTE());
        args.put(SOC_ROW_ID, actualite.getSOC().getIDENTIFICATEUR());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }

}