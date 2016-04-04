package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Perturbation {

    @SerializedName("id_perturbation")
    @Expose
    private int ROW_ID;
    @SerializedName("text")
    @Expose
    private String TEXTE;
    @SerializedName("date_perturbation")
    @Expose
    private String DATE;
    @SerializedName("ligne")
    @Expose
    private Ligne LIG;

    public Perturbation() {
    }

    public Perturbation(int ROW_ID, String TEXTE, String DATE, Ligne LIG) {
        this.ROW_ID = ROW_ID;
        this.TEXTE = TEXTE;
        this.DATE = DATE;
        this.LIG = LIG;
    }

    @Override
    public String toString() {
        return "Perturbation{" +
                "ROW_ID=" + ROW_ID +
                ", TEXTE='" + TEXTE + '\'' +
                ", DATE='" + DATE + '\'' +
                ", LIG=" + LIG +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public String getTEXTE() {
        return TEXTE;
    }

    public void setTEXTE(String TEXTE) {
        this.TEXTE = TEXTE;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public Ligne getLIG() {
        return LIG;
    }

    public void setLIG(Ligne LIG) {
        this.LIG = LIG;
    }
}
