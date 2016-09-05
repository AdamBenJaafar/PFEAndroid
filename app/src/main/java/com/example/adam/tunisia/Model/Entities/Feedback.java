package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.SerializedName;

public class Feedback {

    private transient int ROW_ID;

    @SerializedName("date_feed")
    private String DATE;
    @SerializedName("note")
    private String NOTE;
    @SerializedName("text")
    private String TEXTE;
    @SerializedName("email")
    private String EMAIL;

    private transient int SOC_ROW_ID;

    public Feedback() {
    }

    public Feedback(String date, String note, String texte, String email, int SOC_ROW_ID) {
        DATE = date;
        NOTE = note;
        TEXTE = texte;
        EMAIL = email;
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "ROW_ID=" + ROW_ID +
                ", DATE='" + DATE + '\'' +
                ", NOTE=" + NOTE +
                ", TEXTE='" + TEXTE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", SOC_ROW_ID=" + SOC_ROW_ID +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public String getNOTE() {
        return NOTE;
    }

    public String getTEXTE() {
        return TEXTE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public int getSOC_ROW_ID() {
        return SOC_ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    public void setTEXTE(String TEXTE) {
        this.TEXTE = TEXTE;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setSOC_ROW_ID(int SOC_ROW_ID) {
        this.SOC_ROW_ID = SOC_ROW_ID;
    }
    
}

