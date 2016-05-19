package com.example.adam.tunisia.Presenter.Helpers;


import android.content.Context;
import android.graphics.Color;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

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

    /* ***************************************************************
                        BuILD MAIN GRAPH
    *************************************************************** */
    public static ArrayList<ArrayList<Integer>> GraphBuilder(){
        ArrayList<ArrayList<Integer>> A = new ArrayList<ArrayList<Integer>>();
        return A;
    }


    /* ***************************************************************
                        DIJKSTRA IT
    *************************************************************** */
    public static ArrayList<Station> Calculate(LatLng A, LatLng B){


        return new ArrayList<>();
    }

    public ArrayList<Integer> BuildGraph(Context C,int f, int t){

        // Initializing database adapters
        DBAdapterStation DBAS = new DBAdapterStation(C);
        DBAdapterLigne DBAL = new DBAdapterLigne(C);
        DBAdapterStation_Ligne DBASL = new DBAdapterStation_Ligne(C);
        DBAL.open();
        DBASL.open();
        DBAS.open();

        // Getting all Stations
        ArrayList<Station> LS = DBAS.getAllStation();

        // Hashmap: Each Station with a list of neigboor stations wtih distances
        HashMap<Integer,ArrayList<SD>> HM = new HashMap<Integer,ArrayList<SD>>();

        for(int i=0;i <LS.size(); i++){
            HM.put(LS.get(i).getROW_ID(),new ArrayList<SD>());
        }

        // Getting all Lignes
        ArrayList<Ligne> LL = DBAL.getAllLigne();


        for( Ligne L : LL){

            if(L.getDIRECTION().equals("aller")){

            // Getting Ligne details
            ArrayList<Station_Ligne> LSL = DBASL.getAllStation_LigneByLigne(L.getROW_ID());
            Collections.sort(LSL);

            for( int i=1; i < LSL.size() ; i++){

                Station_Ligne A = LSL.get(i);
                A.setSTATION(DBAS.getStation(A.getSTATION().getROW_ID()));

                Station_Ligne B = LSL.get(i-1);
                B.setSTATION(DBAS.getStation(B.getSTATION().getROW_ID()));

                double LAT1 = Double.parseDouble(A.getSTATION().getLATITUDE());
                double LNG1 = Double.parseDouble(A.getSTATION().getLONGITUDE());
                double LAT2 = Double.parseDouble(B.getSTATION().getLATITUDE());
                double LNG2 = Double.parseDouble(B.getSTATION().getLONGITUDE());

                double X = distFrom(LAT1,LNG1,LAT2,LNG2);

                //faux ecause going to one sense
                if(!HM.get(A.getSTATION().getROW_ID()).contains(new SD(X,B.getSTATION().getROW_ID())))
                {
                    HM.get(A.getSTATION().getROW_ID()).add(new SD(X, B.getSTATION().getROW_ID()));
                    HM.get(B.getSTATION().getROW_ID()).add(new SD(X, A.getSTATION().getROW_ID()));
                }

            }

            }
        }



        for(int i=0;i <LS.size(); i++){
            for(int j=i+1 ; j < LS.size() ; j++ ){

                double LAT1 = Double.parseDouble(LS.get(i).getLATITUDE());
                double LNG1 = Double.parseDouble(LS.get(i).getLONGITUDE());

                double LAT2 = Double.parseDouble(LS.get(j).getLATITUDE());
                double LNG2 = Double.parseDouble(LS.get(j).getLONGITUDE());

                double X = distFrom(LAT1,LNG1,LAT2,LNG2);

                if( X < 2000 ){

                    HM.get(LS.get(i).getROW_ID()).add(new SD((X*4),LS.get(j).getROW_ID()));
                    HM.get(LS.get(j).getROW_ID()).add(new SD((X*4),LS.get(i).getROW_ID()));

                }

            }
        }



        for(Map.Entry<Integer,ArrayList<SD>> entry : HM.entrySet() ){
            Integer key = entry.getKey();
            ArrayList<SD> value = entry.getValue();

            System.out.println(key + " " + value.size() + " " + value.toString() );

        }


        int from = f;
        int to = t;



        HashMap<Integer,Double> HM1 = new HashMap<Integer,Double>() ;
        HashMap<Integer,ArrayList<Integer>> HM2 = new HashMap<Integer,ArrayList<Integer>>() ;

        for(int i=0;i <LS.size(); i++){
            HM1.put(LS.get(i).getROW_ID(),0.0);
            HM2.put(LS.get(i).getROW_ID(),new ArrayList<Integer>());
        }

        Queue Q = new LinkedList<Integer>();

        HM1.put(from,0.0);
        HM2.put(from,new ArrayList<Integer>());
        HM2.get(from).add(from);

        Q.add(from);

        while(!Q.isEmpty()){

            int current = (Integer)Q.remove();

            System.out.println(" For station  " + current );

            for( SD neighbor : HM.get(current) ){

                double distance =  neighbor.D + HM1.get(current) ;

                ArrayList<Integer> way = (ArrayList)HM2.get(current).clone() ;
                way.add( neighbor.S );

                if(  (distance < HM1.get(neighbor.S)) || HM1.get(neighbor.S)==0.0 ){

                    HM1.put(neighbor.S,distance);

                    HM2.put(neighbor.S,way);


                    System.out.println("now , the way to " + neighbor.S + " is " + HM2.get(neighbor.S).toString() );

                    Q.add(neighbor.S);

                }

            }

        }

        System.out.println(" Distance to destination = " + HM1.get(to));
        System.out.println(" Way to destination = " + HM2.get(to).toString() );

        DBAL.close();
        DBASL.close();
        DBAS.close();


        return HM2.get(to);

    }

    public class SD{

        public double D;
        public int S;

        @Override
        public String toString() {
            return "SD{" +
                    "D=" + D +
                    ", S=" + S +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SD)) return false;
            SD sd = (SD) o;
            return S == sd.S;
        }

        @Override
        public int hashCode() {
            return Objects.hash(D, S);
        }

        public SD(double d, int s) {
            D = d;
            S = s;
        }
    }

}
