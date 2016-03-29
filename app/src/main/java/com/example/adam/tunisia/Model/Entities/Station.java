package com.example.adam.tunisia.Model.Entities;

import com.google.android.gms.maps.model.LatLng;

public class Station {

    private int ROW_ID;
    private String NOM;
    private String TYPE;
    private LatLng LATLNG;
    private int MAJEURE;
    private int SOC_ROW_ID;

    public Station() {
    }

    public Station(String nom, String type, com.google.android.gms.maps.model.LatLng latLng, int majeure, int SOC_ROW_ID) {
        NOM = nom;
        TYPE = type;
        LATLNG = latLng;
        MAJEURE = majeure;
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

    @Override
    public String toString() {
        return "Station{" +
                "ROW_ID=" + ROW_ID +
                ", NOM='" + NOM + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", LATLNG=" + LATLNG +
                ", MAJEURE=" + MAJEURE +
                ", SOC_ROW_ID=" + SOC_ROW_ID +
                '}';
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

    public com.google.android.gms.maps.model.LatLng getLATLNG() {
        return LATLNG;
    }

    public int getMAJEURE() {
        return MAJEURE;
    }

    public int getSOC_ROW_ID() {
        return SOC_ROW_ID;
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

    public void setLATLNG(com.google.android.gms.maps.model.LatLng LATLNG) {
        this.LATLNG = LATLNG;
    }

    public void setMAJEURE(int MAJEURE) {
        this.MAJEURE = MAJEURE;
    }

    public void setSOC_ROW_ID(int SOC_ROW_ID) {
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

}
