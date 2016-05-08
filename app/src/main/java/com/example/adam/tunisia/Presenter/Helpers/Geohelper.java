package com.example.adam.tunisia.Presenter.Helpers;


import android.graphics.Color;

public class GeoHelper {

    public static int [] Colors = {Color.BLUE,Color.GREEN,Color.RED,Color.YELLOW,Color.CYAN,Color.MAGENTA,  Color.BLUE,Color.WHITE};


    // CALCULATE DISTANCE IN METERS BETWEEN TWO POINTS
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (double) (earthRadius * c);

        return dist;
    }

    // CALCULATE ZOOM LEVEL FROM DISTANCE
    public static int getZoomLevel(double radius) {
        int zoomLevel=15;
        double scale = radius / 500;
        zoomLevel =(int) (16 - Math.log(scale) / Math.log(2));
        if(zoomLevel<=5)
            zoomLevel=15;
        return zoomLevel;
    }

}
