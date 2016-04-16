package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("auto_Station")
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
    @SerializedName("principale")
    @Expose
    private boolean PRINCIPALE;
    @SerializedName("societeID")
    @Expose
    private long SOCIETE_ID;

    public Station() {
    }

    public Station(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public Station(int ROW_ID, String NOM, String TYPE, String LATITUDE, String LONGITUDE, boolean PRINCIPALE, long SOCIETE_ID) {
        this.ROW_ID = ROW_ID;
        this.NOM = NOM;
        this.TYPE = TYPE;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.PRINCIPALE = PRINCIPALE;
        this.SOCIETE_ID = SOCIETE_ID;
    }

    @Override
    public String toString() {
        return "Station{" +
                "SOCIETE_ID=" + SOCIETE_ID +
                ", PRINCIPALE=" + PRINCIPALE +
                ", LONGITUDE='" + LONGITUDE + '\'' +
                ", LATITUDE='" + LATITUDE + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", NOM='" + NOM + '\'' +
                ", ROW_ID=" + ROW_ID +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public String getNOM() {
        return NOM;
    }

    public void setNOM(String NOM) {
        this.NOM = NOM;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public boolean isPRINCIPALE() {
        return PRINCIPALE;
    }

    public void setPRINCIPALE(boolean PRINCIPALE) {
        this.PRINCIPALE = PRINCIPALE;
    }

    public long getSOCIETE_ID() {
        return SOCIETE_ID;
    }

    public void setSOCIETE_ID(long SOCIETE_ID) {
        this.SOCIETE_ID = SOCIETE_ID;
    }
}
