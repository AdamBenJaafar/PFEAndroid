package com.example.adam.tunisia.Model.Entities;

public class Feedback {

    private int ROW_ID;
    private String COMPAGNIENOM;
    private String DATE;
    private int NOTE;
    private String TEXTE;
    private String EMAIL;

    public Feedback(int ID,String COMPAGNIENOM, String DATE, int NOTE, String TEXTE, String EMAIL) {
        this.ROW_ID = ID;
        this.COMPAGNIENOM = COMPAGNIENOM;
        this.DATE = DATE;
        this.NOTE = NOTE;
        this.TEXTE = TEXTE;
        this.EMAIL = EMAIL;
    }

    public Feedback() {
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "ID=" + ROW_ID +
                ", COMPAGNIENOM='" + COMPAGNIENOM + '\'' +
                ", DATE='" + DATE + '\'' +
                ", NOTE=" + NOTE +
                ", TEXTE='" + TEXTE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                '}';
    }

    public int getID() {
        return ROW_ID;
    }

    public void setID(int ID) {
        this.ROW_ID = ID;
    }

    public String getCOMPAGNIENOM() {
        return COMPAGNIENOM;
    }

    public void setCOMPAGNIENOM(String COMPAGNIENOM) {
        this.COMPAGNIENOM = COMPAGNIENOM;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public int getNOTE() {
        return NOTE;
    }

    public void setNOTE(int NOTE) {
        this.NOTE = NOTE;
    }

    public String getTEXTE() {
        return TEXTE;
    }

    public void setTEXTE(String TEXTE) {
        this.TEXTE = TEXTE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

}

