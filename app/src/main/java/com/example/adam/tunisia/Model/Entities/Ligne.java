package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ligne {

    @SerializedName("auto_ligne")
    @Expose
    private int ROW_ID;
    @SerializedName("direction")
    @Expose
    private String DIRECTION;
    @SerializedName("id")
    @Expose
    private String IDENTIFIANT;
    @SerializedName("type")
    @Expose
    private String TYPE;
    @SerializedName("societe")
    @Expose
    private Societe SOC;

    public Ligne() {
    }

    public Ligne(String IDENTIFIANT) {
        this.IDENTIFIANT = IDENTIFIANT;
    }

    public Ligne(int ROW_ID, String DIRECTION, String IDENTIFIANT, String TYPE, Societe SOC) {
        this.ROW_ID = ROW_ID;
        this.DIRECTION = DIRECTION;
        this.IDENTIFIANT = IDENTIFIANT;
        this.TYPE = TYPE;
        this.SOC = SOC;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "ROW_ID=" + ROW_ID +
                ", DIRECTION='" + DIRECTION + '\'' +
                ", IDENTIFIANT='" + IDENTIFIANT + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", SOC=" + SOC +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public String getDIRECTION() {
        return DIRECTION;
    }

    public void setDIRECTION(String DIRECTION) {
        this.DIRECTION = DIRECTION;
    }

    public String getIDENTIFIANT() {
        return IDENTIFIANT;
    }

    public void setIDENTIFIANT(String IDENTIFIANT) {
        this.IDENTIFIANT = IDENTIFIANT;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public Societe getSOC() {
        return SOC;
    }

    public void setSOC(Societe SOC) {
        this.SOC = SOC;
    }
}

