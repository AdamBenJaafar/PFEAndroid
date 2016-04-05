package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Station_Ligne {

    @SerializedName("auto_Station_Ligne")
    @Expose
    private int ROW_ID;
    @SerializedName("station")
    @Expose
    private Station STATION;
    @SerializedName("ligne")
    @Expose
    private Ligne LIGNE;
    @SerializedName("pos")
    @Expose
    private int POSITION;
    @SerializedName("horaires")
    @Expose
    private List<String> HORAIRES;

    public Station_Ligne() {
    }

    public Station_Ligne(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public Station_Ligne(int ROW_ID, Station STATION, Ligne LIGNE, int POSITION, List<String> HORAIRES) {
        this.ROW_ID = ROW_ID;
        this.STATION = STATION;
        this.LIGNE = LIGNE;
        this.POSITION = POSITION;
        this.HORAIRES = HORAIRES;
    }

    @Override
    public String toString() {
        return "Station_Ligne{" +
                "ROW_ID=" + ROW_ID +
                ", STATION=" + STATION +
                ", LIGNE=" + LIGNE +
                ", POSITION=" + POSITION +
                ", HORAIRES=" + HORAIRES +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public Station getSTATION() {
        return STATION;
    }

    public void setSTATION(Station STATION) {
        this.STATION = STATION;
    }

    public Ligne getLIGNE() {
        return LIGNE;
    }

    public void setLIGNE(Ligne LIGNE) {
        this.LIGNE = LIGNE;
    }

    public int getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(int POSITION) {
        this.POSITION = POSITION;
    }

    public List<String> getHORAIRES() {
        return HORAIRES;
    }

    public void setHORAIRES(List<String> HORAIRES) {
        this.HORAIRES = HORAIRES;
    }
}
