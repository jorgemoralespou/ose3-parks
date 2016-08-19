package com.openshift.evangelists.roadshow.model;

/**
 * Created by jmorales on 19/08/16.
 */
public class DefaultPark implements Park {

    private Object id;
    private Object name;
    private Object longitude;
    private Object latitude;

    public DefaultPark() {
    }

    public DefaultPark(String id, String name, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public Object getName() {
        return name;
    }

    @Override
    public void setName(Object name) {
        this.name = name;
    }

    @Override
    public Object getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    @Override
    public Object getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "DefaultPark{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
