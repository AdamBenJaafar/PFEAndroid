package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("auto_station")
    @Expose
    private int ROW_ID;
    @SerializedName("nom")
    @Expose
    private String NOM;
    @SerializedName("type")
    @Expose
    private String TYPE;
    @SerializedName("latitude")
    @Expose
    private String LATITUDE;
    @SerializedName("longitude")
    @Expose
    private String LONGITUDE;
    @SerializedName("majeure")
    @Expose
    private boolean MAJEURE;
    @SerializedName("societe")
    @Expose
    private Societe SOC;


    public Station() {
    }

    public Station(int ROW_ID, String NOM, String TYPE, String LATITUDE, String LONGITUDE, boolean MAJEURE, Societe SOC) {
        this.ROW_ID = ROW_ID;
        this.NOM = NOM;
        this.TYPE = TYPE;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.MAJEURE = MAJEURE;
        this.SOC = SOC;
    }

    @Override
    public String toString() {
        return "Station{" +
                "ROW_ID=" + ROW_ID +
                ", NOM='" + NOM + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", LATITUDE='" + LATITUDE + '\'' +
                ", LONGITUDE='" + LONGITUDE + '\'' +
                ", MAJEURE=" + MAJEURE +
                ", SOC=" + SOC +
                '}';
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setNOM(String NOM) {
        this.NOM = NOM;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public void setMAJEURE(boolean MAJEURE) {
        this.MAJEURE = MAJEURE;
    }

    public void setSOC(Societe SOC) {
        this.SOC = SOC;
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public String getNOM() {
        return NOM;
    }

    public String getTYPE() {
        return TYPE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public boolean isMAJEURE() {
        return MAJEURE;
    }

    public Societe getSOC() {
        return SOC;
    }

}
