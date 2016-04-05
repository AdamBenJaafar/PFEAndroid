package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Station_Ligne_Horaire {

    @SerializedName("auto_Station_Ligne_Horaire")
    @Expose
    private Station_Ligne STATION_LIGNE;
    @SerializedName("horaires")
    @Expose
    private String HORAIRE;

    public Station_Ligne_Horaire() {
    }

    public Station_Ligne_Horaire(Station_Ligne STATION_LIGNE, String HORAIRE) {
        this.STATION_LIGNE = STATION_LIGNE;
        this.HORAIRE = HORAIRE;
    }

    @Override
    public String toString() {
        return "Station_Ligne_Horaire{" +
                "STATION_LIGNE=" + STATION_LIGNE +
                ", HORAIRE='" + HORAIRE + '\'' +
                '}';
    }

    public Station_Ligne getSTATION_LIGNE() {
        return STATION_LIGNE;
    }

    public void setSTATION_LIGNE(Station_Ligne STATION_LIGNE) {
        this.STATION_LIGNE = STATION_LIGNE;
    }

    public String getHORAIRE() {
        return HORAIRE;
    }

    public void setHORAIRE(String HORAIRE) {
        this.HORAIRE = HORAIRE;
    }
}
