package com.example.adam.tunisia.Model.Entities;

public class Ligne {

    private int ROW_ID;
    private String DIRECTION;
    private String IDENTIFIANT;
    private String TYPE;
    private int SOC_ROW_ID;

    public Ligne() {
    }

    public Ligne(String DIRECTION, String IDENTIFIANT, String TYPE, int SOC_ROW_ID) {
        this.DIRECTION = DIRECTION;
        this.IDENTIFIANT = IDENTIFIANT;
        this.TYPE = TYPE;
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "ROW_ID=" + ROW_ID +
                ", DIRECTION='" + DIRECTION + '\'' +
                ", IDENTIFIANT='" + IDENTIFIANT + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", SOC_ROW_ID=" + SOC_ROW_ID +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public String getDIRECTION() {
        return DIRECTION;
    }

    public String getIDENTIFIANT() {
        return IDENTIFIANT;
    }

    public String getTYPE() {
        return TYPE;
    }

    public int getSOC_ROW_ID() {
        return SOC_ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setDIRECTION(String DIRECTION) {
        this.DIRECTION = DIRECTION;
    }

    public void setIDENTIFIANT(String IDENTIFIANT) {
        this.IDENTIFIANT = IDENTIFIANT;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public void setSOC_ROW_ID(int SOC_ROW_ID) {
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

}

