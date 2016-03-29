package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.adam.tunisia.Model.Entities.Feedback;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterFeedback extends AdapterDB {

    public static final String TAG = "DBAdapterFeedback";

    public static final String ROW_ID = "_id";
    public static final String COMPAGNIENOM = "compagnienom";
    public static final String DATE = "date";
    public static final String NOTE = "note";
    public static final String TEXTE = "texte";
    public static final String EMAIL = "email";

    public static final int COL_ROW_ID = 0;
    public static final int COL_COMPAGNIENOM = 1;
    public static final int COL_DATE = 2;
    public static final int COL_NOTE = 3;
    public static final int COL_TEXTE = 4;
    public static final int COL_EMAIL = 5;

    String [] ALL_KEYS = { ROW_ID, COMPAGNIENOM, DATE, NOTE, TEXTE, EMAIL };

    private static final String DATABASE_TABLE = "feedback";

    private SQLiteDatabase mDb;

    // _____ CONSTRUCTOR, OPEN, CLOSE _____

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

    // _____ CREATE, READ, UPDATE, DELETE _____

    public long createFeedback(Feedback feedback){
        ContentValues initialValues = new ContentValues();
        initialValues.put(DATE, feedback.getDATE());
        initialValues.put(COMPAGNIENOM, feedback.getCOMPAGNIENOM());
        initialValues.put(NOTE, feedback.getNOTE());
        initialValues.put(TEXTE, feedback.getTEXTE());
        initialValues.put(EMAIL, feedback.getEMAIL());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteFeedback(long rowId) {
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Feedback> getAllFeedback() {
        ArrayList<Feedback> A= new ArrayList<Feedback>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Feedback F= new Feedback();

                // Process the data:
                int id = mCursor.getInt(0);
                String compagnienom = mCursor.getString(1);
                String date = mCursor.getString(2);
                int note = mCursor.getInt(3);
                String texte = mCursor.getString(4);
                String email = mCursor.getString(5);

                // Append data to the message:
                F.setID(id);
                F.setCOMPAGNIENOM(compagnienom);
                F.setDATE(date);
                F.setNOTE(note);
                F.setTEXTE(texte);
                F.setEMAIL(email);

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Feedback getFeedback(long rowId) throws SQLException {

        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Feedback F= new Feedback();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(0);
                String compagnienom = mCursor.getString(1);
                String date = mCursor.getString(2);
                int note = mCursor.getInt(3);
                String texte = mCursor.getString(4);
                String email = mCursor.getString(5);

                // Append data to the message:
                F.setID(id);
                F.setCOMPAGNIENOM(compagnienom);
                F.setDATE(date);
                F.setNOTE(note);
                F.setTEXTE(texte);
                F.setEMAIL(email);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return F;
    }

    public void deleteAll() {
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

    public boolean updateFeedback(Feedback feedback){

        String where = ROW_ID + "=" + feedback.getID();

        ContentValues args = new ContentValues();
        args.put(DATE, feedback.getDATE());
        args.put(COMPAGNIENOM, feedback.getCOMPAGNIENOM());
        args.put(NOTE, feedback.getNOTE());
        args.put(TEXTE, feedback.getTEXTE());
        args.put(EMAIL, feedback.getEMAIL());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }

}