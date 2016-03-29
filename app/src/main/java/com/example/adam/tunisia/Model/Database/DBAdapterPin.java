package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.adam.tunisia.Model.Entities.Pin;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterPin extends AdapterDB {

    public static final String TAG = "DBAdapterPin";

    public static final String ROW_ID = "_id";
    public static final String NAME = "name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";

    public static final int COL_ROW_ID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_LATITUDE = 2;
    public static final int COL_LONGITUDE = 3;
    public static final int COL_TYPE = 4;
    public static final int COL_DESCRIPTION = 5;

    String [] ALL_KEYS = { ROW_ID, NAME, LATITUDE, LONGITUDE, TYPE, DESCRIPTION};

    private static final String DATABASE_TABLE = "pin";

    private SQLiteDatabase mDb;

    // _____ CONSTRUCTOR, OPEN, CLOSE _____

    public DBAdapterPin(Context ctx) {
        super(ctx);
    }

    public DBAdapterPin open() {
        super.open();
        return this;
    }

    @Override
    public void close() {
        super.close();
    }

    // _____ CREATE, READ, UPDATE, DELETE _____

    public long createPin(Pin pin){
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, pin.getNAME());
        initialValues.put(LATITUDE, pin.getLATLNG().latitude);
        initialValues.put(LONGITUDE, pin.getLATLNG().longitude);
        initialValues.put(TYPE, pin.getTYPE());
        initialValues.put(DESCRIPTION, pin.getDESCRIPTION());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deletePin(long rowId) {
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Pin> getAllPin() {
        ArrayList<Pin> A= new ArrayList<Pin>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Pin P= new Pin();

                // Process the data:
                int row_id = mCursor.getInt(0);
                String name = mCursor.getString(1);
                double latitude = mCursor.getDouble(2);
                double longitude = mCursor.getDouble(3);
                String type = mCursor.getString(4);
                String description = mCursor.getString(5);

                // Append data to the message:
                P.setROW_ID(row_id);
                P.setNAME(name);
                P.setLATLNG(new LatLng(latitude, longitude));
                P.setTYPE(type);
                P.setDESCRIPTION(description);

                A.add(P);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Pin getPin(long rowId) throws SQLException {

        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Pin P= new Pin();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int row_id = mCursor.getInt(0);
                String name = mCursor.getString(1);
                double latitude = mCursor.getDouble(2);
                double longitude = mCursor.getDouble(3);
                String type = mCursor.getString(4);
                String description = mCursor.getString(5);

                // Append data to the message:
                P.setROW_ID(row_id);
                P.setNAME(name);
                P.setLATLNG(new LatLng(latitude,longitude));
                P.setTYPE(type);
                P.setDESCRIPTION(description);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return P;
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

    public boolean updatePin(Pin pin){

        String where = ROW_ID + "=" + pin.getROW_ID();

        ContentValues args = new ContentValues();
        args.put(NAME, pin.getNAME());
        args.put(LATITUDE, pin.getLATLNG().latitude);
        args.put(LONGITUDE, pin.getLATLNG().longitude);
        args.put(TYPE, pin.getTYPE());
        args.put(DESCRIPTION, pin.getDESCRIPTION());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }

}