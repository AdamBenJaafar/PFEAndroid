package com.example.adam.tunisia.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Station_Ligne implements Comparable<Station_Ligne> {

    @SerializedName("auto_Station_Ligne")
    @Expose
    private int ROW_ID;
    @SerializedName("station")
    @Expose
    private Station STATION;
    @SerializedName("ligne")
    @Expose
    private Ligne LIGNE;
    @SerializedName("pos")
    @Expose
    private int POSITION;
    @SerializedName("horaires")
    @Expose
    private List<String> HORAIRES;

    public Station_Ligne() {
    }

    public Station_Ligne(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public Station_Ligne(int ROW_ID, Station STATION, Ligne LIGNE, int POSITION, List<String> HORAIRES) {
        this.ROW_ID = ROW_ID;
        this.STATION = STATION;
        this.LIGNE = LIGNE;
        this.POSITION = POSITION;
        this.HORAIRES = HORAIRES;
    }

    @Override
    public String toString() {
        return "Station_Ligne{" +
                "ROW_ID=" + ROW_ID +
                ", STATION=" + STATION +
                ", LIGNE=" + LIGNE +
                ", POSITION=" + POSITION +
                ", HORAIRES=" + HORAIRES +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public Station getSTATION() {
        return STATION;
    }

    public void setSTATION(Station STATION) {
        this.STATION = STATION;
    }

    public Ligne getLIGNE() {
        return LIGNE;
    }

    public void setLIGNE(Ligne LIGNE) {
        this.LIGNE = LIGNE;
    }

    public int getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(int POSITION) {
        this.POSITION = POSITION;
    }

    public List<String> getHORAIRES() {
        return HORAIRES;
    }

    public void setHORAIRES(List<String> HORAIRES) {
        this.HORAIRES = HORAIRES;
    }

    @Override
    public int compareTo(Station_Ligne another) {
        if(another.getPOSITION()>this.getPOSITION()){
            return -1;
        }else{
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station_Ligne)) return false;

        Station_Ligne that = (Station_Ligne) o;

        if (getROW_ID() != that.getROW_ID()) return false;
        if (getPOSITION() != that.getPOSITION()) return false;
        if (getSTATION() != null ? !getSTATION().equals(that.getSTATION()) : that.getSTATION() != null)
            return false;
        if (getLIGNE() != null ? !getLIGNE().equals(that.getLIGNE()) : that.getLIGNE() != null)
            return false;
        return getHORAIRES() != null ? getHORAIRES().equals(that.getHORAIRES()) : that.getHORAIRES() == null;

    }

    @Override
    public int hashCode() {
        int result = getROW_ID();
        result = 31 * result + (getSTATION() != null ? getSTATION().hashCode() : 0);
        result = 31 * result + (getLIGNE() != null ? getLIGNE().hashCode() : 0);
        result = 31 * result + getPOSITION();
        result = 31 * result + (getHORAIRES() != null ? getHORAIRES().hashCode() : 0);
        return result;
    }
}
