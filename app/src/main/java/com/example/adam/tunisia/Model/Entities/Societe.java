package com.example.adam.tunisia.Model.Entities;

public class Societe {

    private int ROW_ID;
    private String Date;
    private String Details;
    private String FormeJuridique;
    private String Identificateur;
    private String Login;
    private String Logo;
    private String MotDePasse;
    private String NomComplet;
    private String SiegeSocial;
    private String Sigle;
    private String SiteWeb;

    public Societe() {
    }

    public Societe(String siteWeb, String sigle, String siegeSocial, String nomComplet, String motDePasse, String logo, String login, String identificateur, String formeJuridique, String details, String date) {
        SiteWeb = siteWeb;
        Sigle = sigle;
        SiegeSocial = siegeSocial;
        NomComplet = nomComplet;
        MotDePasse = motDePasse;
        Logo = logo;
        Login = login;
        Identificateur = identificateur;
        FormeJuridique = formeJuridique;
        Details = details;
        Date = date;
    }

    @Override
    public String toString() {
        return "Societe{" +
                "ROW_ID=" + ROW_ID +
                ", Date='" + Date + '\'' +
                ", Details='" + Details + '\'' +
                ", FormeJuridique='" + FormeJuridique + '\'' +
                ", Identificateur='" + Identificateur + '\'' +
                ", Login='" + Login + '\'' +
                ", Logo='" + Logo + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                ", NomComplet='" + NomComplet + '\'' +
                ", SiegeSocial='" + SiegeSocial + '\'' +
                ", Sigle='" + Sigle + '\'' +
                ", SiteWeb='" + SiteWeb + '\'' +
                '}';
    }

    public String getDate() {
        return Date;
    }

    public String getDetails() {
        return Details;
    }

    public String getFormeJuridique() {
        return FormeJuridique;
    }

    public String getIdentificateur() {
        return Identificateur;
    }

    public String getLogin() {
        return Login;
    }

    public String getLogo() {
        return Logo;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public String getNomComplet() {
        return NomComplet;
    }

    public String getSiegeSocial() {
        return SiegeSocial;
    }

    public String getSigle() {
        return Sigle;
    }

    public String getSiteWeb() {
        return SiteWeb;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public void setFormeJuridique(String formeJuridique) {
        FormeJuridique = formeJuridique;
    }

    public void setIdentificateur(String identificateur) {
        Identificateur = identificateur;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public void setNomComplet(String nomComplet) {
        NomComplet = nomComplet;
    }

    public void setSiegeSocial(String siegeSocial) {
        SiegeSocial = siegeSocial;
    }

    public void setSigle(String sigle) {
        Sigle = sigle;
    }

    public void setSiteWeb(String siteWeb) {
        SiteWeb = siteWeb;
    }

}
