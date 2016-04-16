package com.example.adam.tunisia.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdapterDB {

    private static final String TAG = "AdapterDB";

    public static final String DATABASE_NAME = "stuffIOwn";

    public static final int DATABASE_VERSION =43;

    private static final String TABLE_SOCIETE = "societe";
    private static final String CREATE_TABLE_SOCIETE =
            "create table " + TABLE_SOCIETE + " (_id integer primary key, "
                    + DBAdapterSociete.DATE+ " TEXT,"
                    + DBAdapterSociete.DETAILS+ " TEXT,"
                    + DBAdapterSociete.FORMEJURIDIQUE+ " TEXT,"
                    + DBAdapterSociete.IDENTIFICATEUR+ " TEXT,"
                    + DBAdapterSociete.LOGIN+ " TEXT,"
                    + DBAdapterSociete.LOGO+ " TEXT,"
                    + DBAdapterSociete.MOTDEPASSE+ " TEXT,"
                    + DBAdapterSociete.NOMCOMPLET+ " TEXT,"
                    + DBAdapterSociete.SIEGESOCIAL+ " TEXT,"
                    + DBAdapterSociete.SIGLE+ " TEXT,"
                    + DBAdapterSociete.SITEWEB+ " TEXT" + ");";

    private static final String TABLE_FEEDBACK = "feedback";
    private static final String CREATE_TABLE_FEEDBACK =
            "create table " + TABLE_FEEDBACK + " (_id integer primary key autoincrement, "
                    + DBAdapterFeedback.DATE+ " TEXT,"
                    + DBAdapterFeedback.NOTE+ " TEXT,"
                    + DBAdapterFeedback.TEXTE+ " TEXT,"
                    + DBAdapterFeedback.EMAIL+ " TEXT,"
                    + DBAdapterFeedback.SOC_ROW_ID+ " INT " + ");";

    private static final String TABLE_STATION = "station";
    private static final String CREATE_TABLE_STATION =
            "create table " + TABLE_STATION + " (_id integer primary key, "
                    + DBAdapterStation.NOM+ " TEXT,"
                    + DBAdapterStation.TYPE+ " TEXT,"
                    + DBAdapterStation.LAT+ " TEXT,"
                    + DBAdapterStation.LNG+ " TEXT,"
                    + DBAdapterStation.PRINCIPALE+ " INT,"
                    + DBAdapterStation.SOC_ROW_ID+ " TEXT " + ");";

    private static final String TABLE_LIGNE = "ligne";
    private static final String CREATE_TABLE_LIGNE =
            "create table " + TABLE_LIGNE + " (_id integer primary key, "
                    + DBAdapterLigne.DIRECTION+ " TEXT,"
                    + DBAdapterLigne.IDENTIFIANT+ " TEXT,"
                    + DBAdapterLigne.TYPE+ " TEXT,"
                    + DBAdapterStation.SOC_ROW_ID+ " TEXT " + ");";

    private static final String TABLE_ACTUALITE = "actualite";
    private static final String CREATE_TABLE_ACTUALITE =
            "create table " + TABLE_ACTUALITE + " (_id integer primary key, "
                    + DBAdapterActualite.TEXTE+ " TEXT,"
                    + DBAdapterActualite.TITRE+ " TEXT,"
                    + DBAdapterActualite.DATE+ " TEXT,"
                    + DBAdapterActualite.SOC_ROW_ID+ " TEXT " + ");";

    private static final String TABLE_PERTURBATION = "perturbation";
    private static final String CREATE_TABLE_PERTURBATION =
            "create table " + TABLE_PERTURBATION + " (_id integer primary key, "
                    + DBAdapterPerturbation.DATE+ " TEXT,"
                    + DBAdapterPerturbation.TEXTE+ " TEXT,"
                    + DBAdapterPerturbation.LIG_ROW_ID+ " TEXT " + ");";

    private static final String TABLE_STATION_LIGNE = "station_ligne";
    private static final String CREATE_TABLE_STATION_LIGNE =
            "create table " + TABLE_STATION_LIGNE + " (_id integer primary key, "
                    + DBAdapterStation_Ligne.STATION_ID+ " INT,"
                    + DBAdapterStation_Ligne.LIGNE_ID+ " INT,"
                    + DBAdapterStation_Ligne.POS+ " INT " + ");";

    private static final String TABLE_STATION_LIGNE_HORAIRES = "station_ligne_horaire";
    private static final String CREATE_TABLE_STATION_LIGNE_HORAIRES =
            "create table " + TABLE_STATION_LIGNE_HORAIRES + " (_id integer , "
                    + DBAdapterStation_Ligne_Horaire.HORAIRE+ " TEXT," +
                    "PRIMARY KEY ( _id, "+ DBAdapterStation_Ligne_Horaire.HORAIRE + "));";


    private static final String TABLE_VEHICULE = "vehicule";
    private static final String CREATE_TABLE_VEHICULE =
            "create table " + TABLE_VEHICULE + " (_id integer , "
                    + DBAdapterVehicule.LIGNE_ID+ " INT,"
                    + DBAdapterVehicule.IMMATRICULATION+ " TEXT " + ");";


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
            db.execSQL(CREATE_TABLE_SOCIETE);
            db.execSQL(CREATE_TABLE_FEEDBACK);
            db.execSQL(CREATE_TABLE_STATION);
            db.execSQL(CREATE_TABLE_LIGNE);
            db.execSQL(CREATE_TABLE_ACTUALITE);
            db.execSQL(CREATE_TABLE_PERTURBATION);
            db.execSQL(CREATE_TABLE_STATION_LIGNE);
            db.execSQL(CREATE_TABLE_STATION_LIGNE_HORAIRES);
            db.execSQL(CREATE_TABLE_VEHICULE);
         // Solution de backup
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {   Log.v(TAG, "Database UPGRADED. onUpgrade method EXECUTED.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOCIETE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIGNE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTUALITE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERTURBATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATION_LIGNE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATION_LIGNE_HORAIRES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICULE);
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