package com.example.adam.tunisia.Model.Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehicule {

    @SerializedName("auto_Vehicule")
    @Expose
    private int ROW_ID;
    @SerializedName("ligne")
    @Expose
    private Ligne LIGNE;
    @SerializedName("immatriculation")
    @Expose
    private String IMMATRICULATION;

    public Vehicule() {
    }

    public Vehicule(int ROW_ID, Ligne LIGNE, String IMMATRICULATION) {
        this.ROW_ID = ROW_ID;
        this.LIGNE = LIGNE;
        this.IMMATRICULATION = IMMATRICULATION;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "ROW_ID=" + ROW_ID +
                ", LIGNE=" + LIGNE +
                ", IMMATRICULATION='" + IMMATRICULATION + '\'' +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public Ligne getLIGNE() {
        return LIGNE;
    }

    public void setLIGNE(Ligne LIGNE) {
        this.LIGNE = LIGNE;
    }

    public String getIMMATRICULATION() {
        return IMMATRICULATION;
    }

    public void setIMMATRICULATION(String IMMATRICULATION) {
        this.IMMATRICULATION = IMMATRICULATION;
    }
}
