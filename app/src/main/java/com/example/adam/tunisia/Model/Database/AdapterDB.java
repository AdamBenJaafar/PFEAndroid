package com.example.adam.tunisia.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdapterDB {

    private static final String TAG = "AdapterDB";

    public static final String DATABASE_NAME = "stuffIOwn";

    public static final int DATABASE_VERSION =15;

    private static final String TABLE_COMPAGNIE = "compagnie";
    private static final String CREATE_TABLE_COMPAGNIE =
            "create table " + TABLE_COMPAGNIE + " (_id integer primary key autoincrement, "
                    + DBAdapterSociete.NAME+ " TEXT,"
                    + DBAdapterSociete.MODEL+ " TEXT,"
                    + DBAdapterSociete.YEAR+ " TEXT" + ");";


    private static final String TABLE_FEEDBACK = "feedback";
    private static final String CREATE_TABLE_FEEDBACK =
            "create table " + TABLE_FEEDBACK + " (_id integer primary key autoincrement, "
                    + DBAdapterFeedback.DATE+ " TEXT,"
                    + DBAdapterFeedback.COMPAGNIENOM+ " TEXT,"
                    + DBAdapterFeedback.NOTE+ " INT,"
                    + DBAdapterFeedback.TEXTE+ " TEXT,"
                    + DBAdapterFeedback.EMAIL+ " TEXT" + ");";

    private static final String TABLE_PIN = "pin";
    private static final String CREATE_TABLE_PIN =
            "create table " + TABLE_PIN + " (_id integer primary key autoincrement, "
                    + DBAdapterPin.NAME+ " TEXT,"
                    + DBAdapterPin.LATITUDE+ " REAL,"
                    + DBAdapterPin.LONGITUDE+ " REAL,"
                    + DBAdapterPin.TYPE+ " TEXT,"
                    + DBAdapterPin.DESCRIPTION+ " TEXT" + ");";



    private final Context context;
    private DatabaseHelper DBHelper;
    protected SQLiteDatabase db;

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {   Log.v(TAG, "Database CREATED. onCreate method EXECUTED.");
            db.execSQL(CREATE_TABLE_COMPAGNIE);
            db.execSQL(CREATE_TABLE_FEEDBACK);
            db.execSQL(CREATE_TABLE_PIN);
         // Solution de backup manquante
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {   Log.v(TAG, "Database UPGRADED. onUpgrade methode EXECUTED.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPAGNIE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIN);
            this.onCreate(db);

        }
    }

    // _____ CONSTRUCTOR - OPEN - CLOSE _____

    public AdapterDB(Context ctx){
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    public AdapterDB open(){
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        this.DBHelper.close();
    }


}