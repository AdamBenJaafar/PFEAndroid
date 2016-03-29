package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class DBAdapterSociete extends AdapterDB {

    public static final String TAG = "DBAdapterSociete";

    public static final String ROW_ID = "_id";
    public static final String NAME = "name";
    public static final String MODEL = "model";
    public static final String YEAR = "year";

    public static final int COL_ROW_ID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_MODEL = 2;
    public static final int COL_YEAR = 3;

    String [] ALL_KEYS = { ROW_ID, NAME, MODEL, YEAR };

    private static final String DATABASE_TABLE = "compagnie";

    private SQLiteDatabase mDb;

    // _____ CONSTRUCTOR, OPEN, CLOSE _____

    public DBAdapterSociete(Context ctx) {
        super(ctx);
    }


    public DBAdapterSociete open() {
        super.open();
        return this;
    }

    @Override
    public void close() {
        super.close();
    }

    // _____ CREATE, READ, UPDATE, DELETE _____

    public long createCompagnie(String name, String model, String year){
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, name);
        initialValues.put(MODEL, model);
        initialValues.put(YEAR, year);
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteCompagnie(long rowId) {

        return this.mDb.delete(DATABASE_TABLE, ROW_ID + "=" + rowId, null) > 0; //$NON-NLS-1$
    }

    public Cursor getAllCompagnie() {

        return this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);
    }

    public Cursor getCompagnie(long rowId) throws SQLException {

        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void deleteAll() {
        Cursor c = getAllCompagnie();
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

    public boolean updateCompagnie(long rowId, String name, String model,
                             String year){

        String where = ROW_ID + "=" + rowId;

        ContentValues args = new ContentValues();
        args.put(NAME, name);
        args.put(MODEL, model);
        args.put(YEAR, year);

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }

}