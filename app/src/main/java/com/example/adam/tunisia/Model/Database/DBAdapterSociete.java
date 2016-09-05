package com.example.adam.tunisia.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adam.tunisia.Model.Entities.Societe;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBAdapterSociete extends AdapterDB {

    public static final String TAG = "DBAdapterSociete";

    public static final String ROW_ID = "_id";
    public static final String DATE = "date";
    public static final String DETAILS = "details";
    public static final String FORMEJURIDIQUE = "formejuridique";
    public static final String IDENTIFICATEUR = "identificateur";
    public static final String LOGIN = "login";
    public static final String LOGO = "logo";
    public static final String MOTDEPASSE = "motdepasse";
    public static final String NOMCOMPLET = "nomcomplet";
    public static final String SIEGESOCIAL = "siegesocial";
    public static final String SIGLE = "sigle";
    public static final String SITEWEB = "siteweb";

    public static final int COL_ROW_ID = 0;
    public static final int COL_DATE = 1;
    public static final int COL_DETAILS = 2;
    public static final int COL_FORMEJURIDIQUE = 3;
    public static final int COL_IDENTIFICATEUR = 4;
    public static final int COL_LOGIN = 5;
    public static final int COL_LOGO = 6;
    public static final int COL_MOTDEPASSE = 7;
    public static final int COL_NOMCOMPLET = 8;
    public static final int COL_SIEGESOCIAL = 9;
    public static final int COL_SIGLE = 10;
    public static final int COL_SITEWEB = 11;

    String [] ALL_KEYS = { ROW_ID, DATE, DETAILS, FORMEJURIDIQUE, IDENTIFICATEUR, LOGIN,LOGO, MOTDEPASSE,NOMCOMPLET,SIEGESOCIAL,SIGLE,SITEWEB};

    private static final String DATABASE_TABLE = "societe";

    private SQLiteDatabase mDb;

     /* ***************************************************************
                        INITIALISATION METHODS
    *************************************************************** */

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

     /* ***************************************************************
                         CRUD METHODS
    *************************************************************** */

    public long createSociete(Societe societe){
        Log.v(TAG, "Societe created");
        ContentValues initialValues = new ContentValues();
        initialValues.put(ROW_ID, societe.getROW_ID());
        initialValues.put(DATE, societe.getDATE());
        initialValues.put(DETAILS, societe.getDETAILS());
        initialValues.put(FORMEJURIDIQUE, societe.getFORMEJURIDIQUE());
        initialValues.put(IDENTIFICATEUR, societe.getIDENTIFICATEUR());
        initialValues.put(LOGIN, societe.getLOGIN());


        initialValues.put(LOGO, societe.getLOGO());

        if(societe.getLOGO()!=null)
            Log.v("ADD LOGO",societe.getLOGO());


        initialValues.put(MOTDEPASSE, societe.getMOTDEPASSE());
        initialValues.put(NOMCOMPLET, societe.getNOMCOMPLET());
        initialValues.put(SIEGESOCIAL, societe.getSIEGESOCIAL());
        initialValues.put(SIGLE, societe.getSIGLE());
        initialValues.put(SITEWEB, societe.getSITEWEB());
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteSociete(long rowId) {
        Log.v(TAG,"Societe deleted");
        String where = ROW_ID + "=" + rowId ;
        return this.mDb.delete(DATABASE_TABLE, where, null) > 0; //$NON-NLS-1$
    }

    public ArrayList<Societe> getAllSociete() {
        Log.v(TAG,"Societe acquired");
        ArrayList<Societe> A= new ArrayList<Societe>();
        Cursor mCursor = this.db.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            do {
                Societe S= new Societe();

                // Process the data:
                long id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String details = mCursor.getString(COL_DETAILS);
                String formejuridique = mCursor.getString(COL_FORMEJURIDIQUE);
                String identificateur = mCursor.getString(COL_IDENTIFICATEUR);
                String login = mCursor.getString(COL_LOGIN);


                String logo = mCursor.getString(COL_LOGO);


                String motdepasse = mCursor.getString(COL_MOTDEPASSE);
                String nomcomplet = mCursor.getString(COL_NOMCOMPLET);
                String siegesocial = mCursor.getString(COL_SIEGESOCIAL);
                String sigle = mCursor.getString(COL_SIGLE);
                String siteweb = mCursor.getString(COL_SITEWEB);



                // Append data to the message:
                S.setROW_ID(id);
                S.setDATE(date);
                S.setDETAILS(details);
                S.setFORMEJURIDIQUE(formejuridique);
                S.setIDENTIFICATEUR(identificateur);
                S.setLOGIN(login);

                S.setLOGO(logo);

                S.setMOTDEPASSE(motdepasse);
                S.setNOMCOMPLET(nomcomplet);
                S.setSIEGESOCIAL(siegesocial);
                S.setSIGLE(sigle);
                S.setSITEWEB(siteweb);



                A.add(S);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();
        return A;
    }

    public Societe getSociete(String SocieteIDENTIFIANT) {
        Log.v(TAG,"Societe acquired");
        String where = IDENTIFICATEUR + " LIKE '%"  + SocieteIDENTIFIANT + "%'";

        Cursor mCursor =

                this.db.query(true, DATABASE_TABLE, null, where, null, null, null, null, null);

        Societe S= new Societe();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                long id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String details = mCursor.getString(COL_DETAILS);
                String formejuridique = mCursor.getString(COL_FORMEJURIDIQUE);
                String identificateur = mCursor.getString(COL_IDENTIFICATEUR);
                String login = mCursor.getString(COL_LOGIN);
                String logo = mCursor.getString(COL_LOGO);
                String motdepasse = mCursor.getString(COL_MOTDEPASSE);
                String nomcomplet = mCursor.getString(COL_NOMCOMPLET);
                String siegesocial = mCursor.getString(COL_SIEGESOCIAL);
                String sigle = mCursor.getString(COL_SIGLE);
                String siteweb = mCursor.getString(COL_SITEWEB);



                // Append data to the message:
                S.setROW_ID(id);
                S.setDATE(date);
                S.setDETAILS(details);
                S.setFORMEJURIDIQUE(formejuridique);
                S.setIDENTIFICATEUR(identificateur);
                S.setLOGIN(login);
                S.setLOGO(logo);
                S.setMOTDEPASSE(motdepasse);
                S.setNOMCOMPLET(nomcomplet);
                S.setSIEGESOCIAL(siegesocial);
                S.setSIGLE(sigle);
                S.setSITEWEB(siteweb);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return S;
    }


    public Societe getSociete(long rowId) throws SQLException {
        Log.v(TAG,"Societe acquired");
        String where = ROW_ID + "=" + rowId;

        Cursor mCursor =

                this.db.query(true, DATABASE_TABLE, null, where, null, null, null, null, null);

        Societe S= new Societe();

        if (mCursor.moveToFirst()) {
            do {
                // Process the data:
                long id = mCursor.getInt(COL_ROW_ID);
                String date = mCursor.getString(COL_DATE);
                String details = mCursor.getString(COL_DETAILS);
                String formejuridique = mCursor.getString(COL_FORMEJURIDIQUE);
                String identificateur = mCursor.getString(COL_IDENTIFICATEUR);
                String login = mCursor.getString(COL_LOGIN);
                String logo = mCursor.getString(COL_LOGO);
                String motdepasse = mCursor.getString(COL_MOTDEPASSE);
                String nomcomplet = mCursor.getString(COL_NOMCOMPLET);
                String siegesocial = mCursor.getString(COL_SIEGESOCIAL);
                String sigle = mCursor.getString(COL_SIGLE);
                String siteweb = mCursor.getString(COL_SITEWEB);



                // Append data to the message:
                S.setROW_ID(id);
                S.setDATE(date);
                S.setDETAILS(details);
                S.setFORMEJURIDIQUE(formejuridique);
                S.setIDENTIFICATEUR(identificateur);
                S.setLOGIN(login);
                S.setLOGO(logo);
                S.setMOTDEPASSE(motdepasse);
                S.setNOMCOMPLET(nomcomplet);
                S.setSIEGESOCIAL(siegesocial);
                S.setSIGLE(sigle);
                S.setSITEWEB(siteweb);

            } while(mCursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        mCursor.close();

        return S;
    }

    public void deleteAll() {
        Log.v(TAG,"Societes deleted");
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

    public boolean updateSociete(Societe societe){
        Log.v(TAG,"Societe updated");
        String where = ROW_ID + "=" + societe.getROW_ID();

        ContentValues initialValues = new ContentValues();
        initialValues.put(DATE, societe.getDATE());
        initialValues.put(DETAILS, societe.getDETAILS());
        initialValues.put(FORMEJURIDIQUE, societe.getFORMEJURIDIQUE());
        initialValues.put(IDENTIFICATEUR, societe.getIDENTIFICATEUR());
        initialValues.put(LOGIN, societe.getLOGIN());
        initialValues.put(LOGO, societe.getLOGO());
        initialValues.put(MOTDEPASSE, societe.getMOTDEPASSE());
        initialValues.put(NOMCOMPLET, societe.getNOMCOMPLET());
        initialValues.put(SIEGESOCIAL, societe.getSIEGESOCIAL());
        initialValues.put(SIGLE, societe.getSIGLE());
        initialValues.put(SITEWEB, societe.getSITEWEB());

        return this.mDb.update(DATABASE_TABLE, initialValues, where, null) >0;
    }

}