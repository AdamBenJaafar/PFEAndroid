package com.example.adam.tunisia.Model.Entities;

public class Ligne {

    private int ROW_ID;
    private String Direction;
    private String Identifiant;
    private String Type;
    private int SOC_ROW_ID;

    public Ligne() {
    }

    public Ligne(String direction, String identifiant, String type, int SOC_ROW_ID) {
        Direction = direction;
        Identifiant = identifiant;
        Type = type;
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "ROW_ID=" + ROW_ID +
                ", Direction='" + Direction + '\'' +
                ", Identifiant='" + Identifiant + '\'' +
                ", Type='" + Type + '\'' +
                ", SOC_ROW_ID=" + SOC_ROW_ID +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public String getDirection() {
        return Direction;
    }

    public String getIdentifiant() {
        return Identifiant;
    }

    public String getType() {
        return Type;
    }

    public int getSOC_ROW_ID() {
        return SOC_ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public void setIdentifiant(String identifiant) {
        Identifiant = identifiant;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setSOC_ROW_ID(int SOC_ROW_ID) {
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

}

