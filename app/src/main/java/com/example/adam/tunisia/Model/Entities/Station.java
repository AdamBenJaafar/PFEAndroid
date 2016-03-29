package com.example.adam.tunisia.Model.Entities;

import com.google.android.gms.maps.model.LatLng;

public class Station {

    private String Name;
    private LatLng LatLng ;
    private Station Next;
    private Station Previous;

    public Station() {
    }

    public Station(String name, com.google.android.gms.maps.model.LatLng latLng) {
        Name = name;
        LatLng = latLng;
        Next = null;
        Previous = null;
    }

    @Override
    public String toString() {
        return "Station{" +
                "Name='" + Name + '\'' +
                ", Next=" + Next +
                ", Previous=" + Previous +
                '}';
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNext(Station next) {
        Next = next;
    }

    public void setPrevious(Station previous) {
        Previous = previous;
    }

    public void setLatLng( com.google.android.gms.maps.model.LatLng latLng) {
        LatLng = latLng;
    }

    public String getName() {
        return Name;
    }

    public Station getNext() {
        return Next;
    }

    public Station getPrevious() {
        return Previous;
    }

    public com.google.android.gms.maps.model.LatLng getLatLng() {
        return LatLng;
    }

}
