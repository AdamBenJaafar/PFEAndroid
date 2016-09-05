package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Feedback;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterFeedback extends AdapterDB {

    public static final String TAG = "DBAdapterFeedback";

    public static final String ROW_ID = "_id";
    public static final String DATE = "date";
    public static final String NOTE = "note";
    public static final String TEXTE = "texte";
    public static final String EMAIL = "email";
    public static final String SOC_ROW_ID = "soc_row_id";

    public static final int COL_ROW_ID = 0;
    public static final int COL_DATE = 1;
    public static final int COL_NOTE = 2;
    public static final int COL_TEXTE = 3;
    public static final int COL_EMAIL = 4;
    public static final int COL_SOC_ROW_ID = 5;

    String [] ALL_KEYS = { ROW_ID, SOC_ROW_ID, DATE, NOTE, TEXTE, EMAIL };

    private static final String DATABASE_TABLE = "feedback";

    private SQLiteDatabase mDb;

    /* ***************************************************************
                        INITIALISATION METHODS
    *************************************************************** */

    public DBAdapterFeedback(Context ctx) {
        super(ctx);
    }

    public DBAdapterFeedback open() {
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

    public long createFeedback(Feedback feedback){
        Log.v(TAG,"Feedback created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(DATE, feedback.getDATE());
        initialValues.put(NOTE, feedback.getNOTE());
        initialValues.put(TEXTE, feedback.getTEXTE());
        initialValues.put(EMAIL, feedback.getEMAIL());
        initialValues.put(SOC_ROW_ID, feedback.getSOC_ROW_ID());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteFeedback(long rowId) {
        Log.v(TAG,"Feedback deleted");
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Feedback> getAllFeedback() {
        Log.v(TAG,"Feedback acquired");
        ArrayList<Feedback> A= new ArrayList<Feedback>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Feedback F= new Feedback();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String note = mCursor.getString(COL_NOTE);
                String texte = mCursor.getString(COL_TEXTE);
                String email = mCursor.getString(COL_EMAIL);
                int soc_row_id = mCursor.getInt(COL_SOC_ROW_ID);

                // Append data to the message:
                F.setROW_ID(id);
                F.setDATE(date);
                F.setNOTE(note);
                F.setTEXTE(texte);
                F.setEMAIL(email);
                F.setSOC_ROW_ID(soc_row_id);

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Feedback getFeedback(long rowId) throws SQLException {
        Log.v(TAG,"Feedback acquired");
        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, null, where, null, null, null, null, null);

        Feedback F= new Feedback();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String note = mCursor.getString(COL_NOTE);
                String texte = mCursor.getString(COL_TEXTE);
                String email = mCursor.getString(COL_EMAIL);
                int soc_row_id = mCursor.getInt(COL_SOC_ROW_ID);

                // Append data to the message:
                F.setROW_ID(id);
                F.setDATE(date);
                F.setNOTE(note);
                F.setTEXTE(texte);
                F.setEMAIL(email);
                F.setSOC_ROW_ID(soc_row_id);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return F;
    }

    public void deleteAll() {
        Log.v(TAG,"Feedbacks deleted");
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

    public boolean updateFeedback(Feedback feedback){
        Log.v(TAG,"Feedback updated");
        String where = ROW_ID + "=" + feedback.getROW_ID();

        ContentValues args = new ContentValues();
        args.put(DATE, feedback.getDATE());
        args.put(NOTE, feedback.getNOTE());
        args.put(TEXTE, feedback.getTEXTE());
        args.put(EMAIL, feedback.getEMAIL());
        args.put(SOC_ROW_ID, feedback.getSOC_ROW_ID());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }

}