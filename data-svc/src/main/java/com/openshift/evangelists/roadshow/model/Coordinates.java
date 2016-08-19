package com.openshift.evangelists.roadshow.model;

import java.util.List;

/**
 * Created by jmorales on 18/08/16.
 */
public class Coordinates {
    public String lat;
    public String lng;

    public Coordinates(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Coordinates(String position) {
        //parse
        this.lat = position;
        this.lng = position;
    }

    public Coordinates(List<Double> position){
        if (position.size()>0)
            this.lat = position.get(0).toString();
        if (position.size()>1)
            this.lng = position.get(1).toString();
    }

    public String getLatitude() {
        return lat;
    }

    public void setLatitude(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return lng;
    }

    public void setLongitude(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
