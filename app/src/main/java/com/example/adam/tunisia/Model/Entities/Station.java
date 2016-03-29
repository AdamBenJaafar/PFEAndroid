package com.example.adam.tunisia.Model.Entities;

import com.google.android.gms.maps.model.LatLng;

public class Station {

    private int ROW_ID;
    private String Nom;
    private String Type;
    private LatLng LatLng ;
    private int Majeure;
    private int SOC_ROW_ID;

    public Station() {
    }

    public Station(String nom, String type, com.google.android.gms.maps.model.LatLng latLng, int majeure, int SOC_ROW_ID) {
        Nom = nom;
        Type = type;
        LatLng = latLng;
        Majeure = majeure;
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

    @Override
    public String toString() {
        return "Station{" +
                "ROW_ID=" + ROW_ID +
                ", Nom='" + Nom + '\'' +
                ", Type='" + Type + '\'' +
                ", LatLng=" + LatLng +
                ", Majeure=" + Majeure +
                ", SOC_ROW_ID=" + SOC_ROW_ID +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public String getNom() {
        return Nom;
    }

    public String getType() {
        return Type;
    }

    public com.google.android.gms.maps.model.LatLng getLatLng() {
        return LatLng;
    }

    public int getMajeure() {
        return Majeure;
    }

    public int getSOC_ROW_ID() {
        return SOC_ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setLatLng(com.google.android.gms.maps.model.LatLng latLng) {
        LatLng = latLng;
    }

    public void setMajeure(int majeure) {
        Majeure = majeure;
    }

    public void setSOC_ROW_ID(int SOC_ROW_ID) {
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

}
