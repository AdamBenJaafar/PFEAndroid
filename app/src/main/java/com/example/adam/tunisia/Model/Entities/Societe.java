package com.example.adam.tunisia.Model.Entities;

import java.util.ArrayList;

public class Societe {

    private String Nom;
    private String Description;
    private ArrayList<Ligne> L;

    public Societe(String nom, String description, ArrayList<Ligne> l) {
        Nom = nom;
        Description = description;
        L = l;
    }

    @Override
    public String toString() {
        return "Societe{" +
                "Nom='" + Nom + '\'' +
                ", Description='" + Description + '\'' +
                ", L=" + L +
                '}';
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ArrayList<Ligne> getL() {
        return L;
    }

    public void setL(ArrayList<Ligne> l) {
        L = l;
    }

}
