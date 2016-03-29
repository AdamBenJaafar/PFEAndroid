package com.example.adam.tunisia.Model.Entities;

import java.util.ArrayList;

public class Ligne {

    private String Name;
    private String Company;
    private ArrayList<Station> A;

    public Ligne(String name, String company, ArrayList<Station> a) {
        Name = name;
        Company = company;
        A = a;
    }

    public Ligne() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public ArrayList<Station> getA() {
        return A;
    }

    public void setA(ArrayList<Station> a) {
        A = a;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "Name='" + Name + '\'' +
                ", Company='" + Company + '\'' +
                ", A=" + A +
                '}';
    }

}

