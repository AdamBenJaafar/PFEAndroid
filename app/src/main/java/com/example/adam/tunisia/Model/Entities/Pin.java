package com.example.adam.tunisia.Model.Entities;

import com.google.android.gms.maps.model.LatLng;

public class Pin {

    private int ROW_ID;
    private String NAME;
    private com.google.android.gms.maps.model.LatLng LATLNG;
    private String TYPE;
    private String DESCRIPTION;

    public Pin(int ROW_ID, String NAME, LatLng LATLNG, String TYPE, String DESCRIPTION) {
        this.ROW_ID = ROW_ID;
        this.NAME = NAME;
        this.LATLNG = LATLNG;
        this.TYPE = TYPE;
        this.DESCRIPTION = DESCRIPTION;
    }

    public Pin() {
    }

    @Override
    public String toString() {
        return "Pin{" +
                "ROW_ID=" + ROW_ID +
                ", NAME='" + NAME + '\'' +
                ", LATLNG=" + LATLNG +
                ", TYPE=" + TYPE +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                '}';
    }

    public int getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public LatLng getLATLNG() {
        return LATLNG;
    }

    public void setLATLNG(LatLng LATLNG) {
        this.LATLNG = LATLNG;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

}
