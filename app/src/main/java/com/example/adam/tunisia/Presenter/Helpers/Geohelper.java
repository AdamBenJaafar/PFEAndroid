package com.example.adam.tunisia.Presenter.Helpers;

import android.content.Context;
import android.graphics.Color;

import com.example.adam.tunisia.Model.Database.DBAdapterLigne;
import com.example.adam.tunisia.Model.Database.DBAdapterStation;
import com.example.adam.tunisia.Model.Database.DBAdapterStation_Ligne;
import com.example.adam.tunisia.Model.Entities.Ligne;
import com.example.adam.tunisia.Model.Entities.Station;
import com.example.adam.tunisia.Model.Entities.Station_Ligne;

import java.util.*;

/** Contains methods for activities using Google Maps and displaying the transport network. */
public class Geohelper {

    /**
     *  Line colors for the Google Maps graph drawing.
     *  This array is needed to display the transport network.
     */
    public static int [] Colors = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.BLUE, Color.WHITE };

    /**
     *  Calculates the Google Maps camera zoom level needed to cover a given radius.
     *  This function is needed to display the transport network.
     */
    public static int getZoomLevel(double radius) {
        int zoomLevel=15;
        double scale = radius / 500;
        zoomLevel =(int) (16 - Math.log(scale) / Math.log(2));
        if(zoomLevel<=5)
            zoomLevel=15;
        return zoomLevel;
    }

    /**
     *  Calculates the distance in meters between two points on the globe.
     *  This function is needed to calculate itineraries.
     */
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

    /**
     *  Searches for the shortest path from point A to B:
     *      _ Builds a graph from all the stations-lines in the database.
     *      _ Looks for the closest station to the arrival and departure point.
     *      _ Searches for the shortest path ( list of stations ) between these stations.
     *
     *      ( Needs a lot of optimisation, I know)
     *
     *      TODO: Clean this shit !
     */
    public ArrayList<Integer> BuildGraph(Context C, int Source, int Destination){

        // Initializing database adapters
        DBAdapterStation DBAS = new DBAdapterStation(C);
        DBAdapterLigne DBAL = new DBAdapterLigne(C);
        DBAdapterStation_Ligne DBASL = new DBAdapterStation_Ligne(C);
        DBAL.open();
        DBASL.open();
        DBAS.open();

        // Getting all Stations
        ArrayList<Station> LS = DBAS.getAllStation();

        // Hashmap: Each Station with a list of neigboor stations with distances
        HashMap<Integer,ArrayList<Station_Distance>> HM = new HashMap<Integer,ArrayList<Station_Distance>>();

        for(int i=0;i <LS.size(); i++){
            HM.put(LS.get(i).getROW_ID(),new ArrayList<Station_Distance>());
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
                if(!HM.get(A.getSTATION().getROW_ID()).contains(new Station_Distance(X,B.getSTATION().getROW_ID())))
                {
                    HM.get(A.getSTATION().getROW_ID()).add(new Station_Distance(X, B.getSTATION().getROW_ID()));
                    HM.get(B.getSTATION().getROW_ID()).add(new Station_Distance(X, A.getSTATION().getROW_ID()));
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

                    HM.get(LS.get(i).getROW_ID()).add(new Station_Distance((X*4),LS.get(j).getROW_ID()));
                    HM.get(LS.get(j).getROW_ID()).add(new Station_Distance((X*4),LS.get(i).getROW_ID()));

                }

            }
        }



        for(Map.Entry<Integer,ArrayList<Station_Distance>> entry : HM.entrySet() ){
            Integer key = entry.getKey();
            ArrayList<Station_Distance> value = entry.getValue();

            System.out.println(key + " " + value.size() + " " + value.toString() );

        }

        HashMap<Integer,Double> HM1 = new HashMap<Integer,Double>() ;
        HashMap<Integer,ArrayList<Integer>> HM2 = new HashMap<Integer,ArrayList<Integer>>() ;

        for(int i=0;i <LS.size(); i++){
            HM1.put(LS.get(i).getROW_ID(),0.0);
            HM2.put(LS.get(i).getROW_ID(),new ArrayList<Integer>());
        }

        Queue<Integer> Q = new LinkedList<Integer>();

        HM1.put(Source,0.0);
        HM2.put(Source,new ArrayList<Integer>());
        HM2.get(Source).add(Source);

        Q.add(Source);

        while(!Q.isEmpty()){

            int current = (Integer)Q.remove();

            System.out.println(" For station  " + current );

            for( Station_Distance neighbor : HM.get(current) ){

                double distance =  neighbor.Distance + HM1.get(current) ;

                ArrayList<Integer> way = (ArrayList)HM2.get(current).clone() ;
                way.add( neighbor.Station_ID);

                if(  (distance < HM1.get(neighbor.Station_ID)) || HM1.get(neighbor.Station_ID)==0.0 ){

                    HM1.put(neighbor.Station_ID,distance);

                    HM2.put(neighbor.Station_ID,way);


                    System.out.println("now , the way to " + neighbor.Station_ID + " is " + HM2.get(neighbor.Station_ID).toString() );

                    Q.add(neighbor.Station_ID);

                }

            }

        }

        System.out.println(" Distance to destination = " + HM1.get(Destination));
        System.out.println(" Way to destination = " + HM2.get(Destination).toString() );

        DBAL.close();
        DBASL.close();
        DBAS.close();


        return HM2.get(Destination);

    }

    /**
     *  Represents a close stations and their distance ( from a known station ).
     */
    public class Station_Distance {

        public double Distance;
        public int Station_ID;

        @Override
        public String toString() {
            return "Station_Distance{" +
                    "Distance=" + Distance +
                    ", Station_ID=" + Station_ID +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Station_Distance)) return false;
            Station_Distance stationDistance = (Station_Distance) o;
            return Station_ID == stationDistance.Station_ID;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Distance, Station_ID);
        }

        public Station_Distance(double distance, int stationID) {
            Distance = distance;
            Station_ID = stationID;
        }
    }

}
