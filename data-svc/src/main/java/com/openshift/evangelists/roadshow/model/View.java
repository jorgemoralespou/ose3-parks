package com.openshift.evangelists.roadshow.model;

/**
 * Created by jmorales on 19/08/16.
 */
public class View{
    private String title;
    private String latitude;
    private String longitude;
    private int zoom;

    public View() {
    }

    public View(String title, String latitude, String longitude, int zoom) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoom = zoom;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    @Override
    public String toString() {
        return "View{" +
                "title='" + title + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", zoom=" + zoom +
                '}';
    }
}
