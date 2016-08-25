package com.openshift.evangelists.roadshow.parks.model;


public class Park implements DataPoint {

    private Object toponymName;
    private Object name;

    private Coordinates position;

    private Object longitude;
    private Object latitude;

    private Object countryCode;
    private Object countryName;

    private Object id;

    public Object getToponymName() {
        return toponymName;
    }

    public void setToponymName(Object toponymName) {
        this.toponymName = toponymName;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Object countryCode) {
        this.countryCode = countryCode;
    }

    public Object getCountryName() {
        return countryName;
    }

    public void setCountryName(Object countryName) {
        this.countryName = countryName;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NationalPark{" +
                "id=" + id +
                ", name=" + name +
                ", toponymName=" + toponymName +
                ", coordinates=" + position +
                ", countryCode=" + countryCode +
                ", countryName=" + countryName +
                '}';
    }
}
