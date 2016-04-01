package com.example.adam.tunisia.Model.Entities;


public class Societe {


    private int ROW_ID;

    private String DATE;

    private String DETAILS;

    private String FORMEJURIDIQUE;

    private String IDENTIFICATEUR;

    private String LOGIN;
    private String LOGO;

    private String MOTDEPASSE;

    private String NOMCOMPLET;

    private String SIEGESOCIAL;

    private String SIGLE;

    private String SITEWEB;

    public Societe() {
    }

    public Societe(String DATE, String DETAILS, String FORMEJURIDIQUE, String IDENTIFICATEUR, String LOGIN, String LOGO, String MOTDEPASSE, String NOMCOMPLET, String SIEGESOCIAL, String SIGLE, String SITEWEB) {
        this.DATE = DATE;
        this.DETAILS = DETAILS;
        this.FORMEJURIDIQUE = FORMEJURIDIQUE;
        this.IDENTIFICATEUR = IDENTIFICATEUR;
        this.LOGIN = LOGIN;
        this.LOGO = LOGO;
        this.MOTDEPASSE = MOTDEPASSE;
        this.NOMCOMPLET = NOMCOMPLET;
        this.SIEGESOCIAL = SIEGESOCIAL;
        this.SIGLE = SIGLE;
        this.SITEWEB = SITEWEB;
    }

    @Override
    public String toString() {
        return "Societe{" +
                "ROW_ID=" + ROW_ID +
                ", DATE='" + DATE + '\'' +
                ", DETAILS='" + DETAILS + '\'' +
                ", FORMEJURIDIQUE='" + FORMEJURIDIQUE + '\'' +
                ", IDENTIFICATEUR='" + IDENTIFICATEUR + '\'' +
                ", LOGIN='" + LOGIN + '\'' +
                ", LOGO='" + LOGO + '\'' +
                ", MOTDEPASSE='" + MOTDEPASSE + '\'' +
                ", NOMCOMPLET='" + NOMCOMPLET + '\'' +
                ", SIEGESOCIAL='" + SIEGESOCIAL + '\'' +
                ", SIGLE='" + SIGLE + '\'' +
                ", SITEWEB='" + SITEWEB + '\'' +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public String getDETAILS() {
        return DETAILS;
    }

    public String getFORMEJURIDIQUE() {
        return FORMEJURIDIQUE;
    }

    public String getIDENTIFICATEUR() {
        return IDENTIFICATEUR;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public String getLOGO() {
        return LOGO;
    }

    public String getMOTDEPASSE() {
        return MOTDEPASSE;
    }

    public String getNOMCOMPLET() {
        return NOMCOMPLET;
    }

    public String getSIEGESOCIAL() {
        return SIEGESOCIAL;
    }

    public String getSIGLE() {
        return SIGLE;
    }

    public String getSITEWEB() {
        return SITEWEB;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public void setDETAILS(String DETAILS) {
        this.DETAILS = DETAILS;
    }

    public void setFORMEJURIDIQUE(String FORMEJURIDIQUE) {
        this.FORMEJURIDIQUE = FORMEJURIDIQUE;
    }

    public void setIDENTIFICATEUR(String IDENTIFICATEUR) {
        this.IDENTIFICATEUR = IDENTIFICATEUR;
    }

    public void setLOGIN(String LOGIN) {
        this.LOGIN = LOGIN;
    }

    public void setLOGO(String LOGO) {
        this.LOGO = LOGO;
    }

    public void setMOTDEPASSE(String MOTDEPASSE) {
        this.MOTDEPASSE = MOTDEPASSE;
    }

    public void setNOMCOMPLET(String NOMCOMPLET) {
        this.NOMCOMPLET = NOMCOMPLET;
    }

    public void setSIEGESOCIAL(String SIEGESOCIAL) {
        this.SIEGESOCIAL = SIEGESOCIAL;
    }

    public void setSIGLE(String SIGLE) {
        this.SIGLE = SIGLE;
    }

    public void setSITEWEB(String SITEWEB) {
        this.SITEWEB = SITEWEB;
    }
}
