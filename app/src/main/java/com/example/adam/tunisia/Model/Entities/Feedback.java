package com.example.adam.tunisia.Model.Entities;

public class Feedback {

    private int ROW_ID;
    private String Date;
    private int Note;
    private String Texte;
    private String Email;
    private int SOC_ROW_ID;

    public Feedback() {
    }

    public Feedback(String date, int note, String texte, String email, int SOC_ROW_ID) {
        Date = date;
        Note = note;
        Texte = texte;
        Email = email;
        this.SOC_ROW_ID = SOC_ROW_ID;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "ROW_ID=" + ROW_ID +
                ", Date='" + Date + '\'' +
                ", Note=" + Note +
                ", Texte='" + Texte + '\'' +
                ", Email='" + Email + '\'' +
                ", SOC_ROW_ID=" + SOC_ROW_ID +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public String getDate() {
        return Date;
    }

    public int getNote() {
        return Note;
    }

    public String getTexte() {
        return Texte;
    }

    public String getEmail() {
        return Email;
    }

    public int getSOC_ROW_ID() {
        return SOC_ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setNote(int note) {
        Note = note;
    }

    public void setTexte(String texte) {
        Texte = texte;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setSOC_ROW_ID(int SOC_ROW_ID) {
        this.SOC_ROW_ID = SOC_ROW_ID;
    }
}

