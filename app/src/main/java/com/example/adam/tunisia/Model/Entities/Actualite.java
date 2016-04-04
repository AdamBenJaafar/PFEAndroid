package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Adam on 04/04/2016.
 */
public class Actualite {

    @SerializedName("id_actualite")
    @Expose
    private int ROW_ID;
    @SerializedName("text")
    @Expose
    private String TEXTE;
    @SerializedName("date_actualite")
    @Expose
    private String DATE;
    @SerializedName("societe")
    @Expose
    private Societe SOC;

    public Actualite() {
    }

    public Actualite(int ROW_ID, String TEXTE, String DATE, Societe soc) {
        this.ROW_ID = ROW_ID;
        this.TEXTE = TEXTE;
        this.DATE = DATE;
        SOC = soc;
    }

    @Override
    public String toString() {
        return "Actualite{" +
                "Soc=" + SOC +
                ", DATE='" + DATE + '\'' +
                ", TEXTE='" + TEXTE + '\'' +
                ", ROW_ID=" + ROW_ID +
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

    public Societe getSOC() {
        return SOC;
    }

    public void setSOC(Societe soc) {
        SOC = soc;
    }
}
