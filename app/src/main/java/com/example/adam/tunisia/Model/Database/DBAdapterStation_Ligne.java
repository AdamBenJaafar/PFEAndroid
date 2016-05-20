package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.example.adam.tunisia.Model.Entities.Societe;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterStation_Ligne extends AdapterDB {

    public static final String TAG = "DBAdapterStation_Ligne";

    public static final String ROW_ID = "_id";
    public static final String STATION_ID = "station_id";
    public static final String LIGNE_ID = "ligne_id";
    public static final String POS = "pos";

    public static final int COL_ROW_ID = 0;
    public static final int COL_STATION_ID = 1;
    public static final int COL_LIGNE_ID = 2;
    public static final int COL_POS = 3;

    String [] ALL_KEYS = { ROW_ID, STATION_ID, LIGNE_ID, POS};

    private static final String DATABASE_TABLE = "station_ligne";

    private SQLiteDatabase mDb;

     /* ***************************************************************
                        INITIALISATION METHODS
    *************************************************************** */

    public DBAdapterStation_Ligne(Context ctx) {
        super(ctx);
    }

    public DBAdapterStation_Ligne open() {
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

    public long createStation_Ligne(Station_Ligne station_ligne){
        Log.v(TAG, "Station_Ligne created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(ROW_ID, station_ligne.getROW_ID());
        initialValues.put(STATION_ID, station_ligne.getSTATION().getROW_ID());
        initialValues.put(LIGNE_ID, station_ligne.getLIGNE().getROW_ID());
        initialValues.put(POS, station_ligne.getPOSITION());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteStation_Ligne(long rowId) {
        Log.v(TAG, "Station_Ligne deleted");
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Station_Ligne> getAllStation_LigneByLigne(int LigneID) {
        Log.v(TAG,"Station_LigneByLigne acquired");
        ArrayList<Station_Ligne> A= new ArrayList<Station_Ligne>();

        String where = LIGNE_ID + "=" + LigneID;

        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , where, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Station_Ligne F= new Station_Ligne();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                int station_id = mCursor.getInt(COL_STATION_ID);
                int ligne_id = mCursor.getInt(COL_LIGNE_ID);
                int soc_row_id = mCursor.getInt(COL_POS);

                // Append data to the message:
                F.setROW_ID(id);
                F.setSTATION(new Station(station_id));
                F.setLIGNE(new Ligne(ligne_id));
                F.setPOSITION(soc_row_id);

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public ArrayList<Station_Ligne> getAllStation_LigneByStation(int StationID) {
        Log.v(TAG,"Station_LigneByLigne acquired");
        ArrayList<Station_Ligne> A= new ArrayList<Station_Ligne>();

        String where = STATION_ID + "=" + StationID;

        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , where, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Station_Ligne F= new Station_Ligne();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                int station_id = mCursor.getInt(COL_STATION_ID);
                int ligne_id = mCursor.getInt(COL_LIGNE_ID);
                int soc_row_id = mCursor.getInt(COL_POS);

                // Append data to the message:
                F.setROW_ID(id);
                F.setSTATION(new Station(station_id));
                F.setLIGNE(new Ligne(ligne_id));
                F.setPOSITION(soc_row_id);

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public ArrayList<Station_Ligne> getAllStation_Ligne() {
        Log.v(TAG,"Station_Ligne acquired");
        ArrayList<Station_Ligne> A= new ArrayList<Station_Ligne>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, ALL_KEYS , null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Station_Ligne F= new Station_Ligne();

                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                int station_id = mCursor.getInt(COL_STATION_ID);
                int ligne_id = mCursor.getInt(COL_LIGNE_ID);
                int soc_row_id = mCursor.getInt(COL_POS);

                // Append data to the message:
                F.setROW_ID(id);
                F.setSTATION(new Station(station_id));
                F.setLIGNE(new Ligne(ligne_id));
                F.setPOSITION(soc_row_id);

                A.add(F);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Station_Ligne getStation_Ligne(long rowId) throws SQLException {
        Log.v(TAG,"Station_Ligne acquired");
        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Station_Ligne F= new Station_Ligne();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                int station_id = mCursor.getInt(COL_STATION_ID);
                int ligne_id = mCursor.getInt(COL_LIGNE_ID);
                int soc_row_id = mCursor.getInt(COL_POS);

                // Append data to the message:
                F.setROW_ID(id);
                F.setSTATION(new Station(station_id));
                F.setLIGNE(new Ligne(ligne_id));
                F.setPOSITION(soc_row_id);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return F;
    }

    public Station_Ligne getStation_LigneBystatByLig(int stat, int lig) {
        Log.v(TAG,"Station_Ligne acquired");

        String where = STATION_ID + "=" + stat + " and "+ LIGNE_ID + "=" + lig ;

        Cursor mCursor =

                this.db.query(true, DATABASE_TABLE, ALL_KEYS , where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        Station_Ligne F= new Station_Ligne();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                int id = mCursor.getInt(COL_ROW_ID);
                int station_id = mCursor.getInt(COL_STATION_ID);
                int ligne_id = mCursor.getInt(COL_LIGNE_ID);
                int soc_row_id = mCursor.getInt(COL_POS);

                // Append data to the message:
                F.setROW_ID(id);
                F.setSTATION(new Station(station_id));
                F.setLIGNE(new Ligne(ligne_id));
                F.setPOSITION(soc_row_id);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return F;
    }

    public void deleteAll() {
        Log.v(TAG,"Station_Lignes deleted");
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

    public boolean updateStation_Ligne(Station_Ligne station_ligne){
        Log.v(TAG,"Station_Ligne updated");
        String where = ROW_ID + "=" + station_ligne.getROW_ID();

        ContentValues args = new ContentValues();
        args.put(STATION_ID, station_ligne.getSTATION().getROW_ID());
        args.put(LIGNE_ID, station_ligne.getLIGNE().getROW_ID());
        args.put(POS, station_ligne.getPOSITION());

        return this.mDb.update(DATABASE_TABLE, args, where, null) >0;
    }



}
