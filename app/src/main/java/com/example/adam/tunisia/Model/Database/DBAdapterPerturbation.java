package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Perturbation;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterPerturbation extends AdapterDB{

    public static final String TAG = "DBAdapterPerturbation";

    public static final String ROW_ID = "_id";
    public static final String TEXTE = "texte";
    public static final String DATE = "date";
    public static final String LIG_ROW_ID = "lig_row_id";

    public static final int COL_ROW_ID = 0;
    public static final int COL_TEXTE = 1;
    public static final int COL_DATE = 2;
    public static final int COL_LIG_ROW_ID = 3;

    String [] ALL_KEYS = { ROW_ID, TEXTE, DATE,LIG_ROW_ID};

    private static final String DATABASE_TABLE = "perturbation";

    private SQLiteDatabase mDb;

    // _____ CONSTRUCTOR, OPEN, CLOSE _____

    public DBAdapterPerturbation(Context ctx) {
        super(ctx);
    }

    public DBAdapterPerturbation open() {
        super.open();
        return this;
    }

    @Override
    public void close() {
        super.close();
    }

    // _____ CREATE, READ, UPDATE, DELETE _____

    public long createPerturbation(Perturbation perturbation){
        Log.v(TAG, "Perturbation created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(ROW_ID, perturbation.getROW_ID());
        initialValues.put(DATE, perturbation.getDATE());
        initialValues.put(TEXTE, perturbation.getTEXTE());
        initialValues.put(LIG_ROW_ID, perturbation.getLIG().getIDENTIFIANT());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deletePerturbation(long rowId) {
        Log.v(TAG,"Perturbation deleted");
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Perturbation> getAllPerturbation() {
        Log.v(TAG,"Perturbation acquired");
        ArrayList<Perturbation> A= new ArrayList<Perturbation>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Perturbation F= new Perturbation();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String texte = mCursor.getString(COL_TEXTE);
                String lig_row_id = mCursor.getString(COL_LIG_ROW_ID);

                // Append data to the message:
                F.setROW_ID(id);
                F.setDATE(date);
                F.setTEXTE(texte);
                F.setLIG(new Ligne(lig_row_id));

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Perturbation getPerturbation(long rowId) throws SQLException {
        Log.v(TAG,"Perturbation acquired");
        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Perturbation F= new Perturbation();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String texte = mCursor.getString(COL_TEXTE);
                String lig_row_id = mCursor.getString(COL_LIG_ROW_ID);

                // Append data to the message:
                F.setROW_ID(id);
                F.setDATE(date);
                F.setTEXTE(texte);
                F.setLIG(new Ligne(lig_row_id));

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return F;
    }

    public void deleteAll() {
        Log.v(TAG,"Perturbations deleted");
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

    public boolean updatePerturbation(Perturbation perturbation){
        Log.v(TAG,"Perturbation updated");
        String where = ROW_ID + "=" + perturbation.getROW_ID();

        ContentValues args = new ContentValues();
        args.put(DATE, perturbation.getDATE());
        args.put(TEXTE, perturbation.getTEXTE());
        args.put(LIG_ROW_ID, perturbation.getLIG().getIDENTIFIANT());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }

}