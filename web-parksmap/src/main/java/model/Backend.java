package model;

import com.openshift.evangelists.roadshow.parks.model.Coordinates;

/**
 * Created by jmorales on 24/08/16.
 */
public class Backend {
    private String name;
    private String displayName;
    private String Url;

    private Coordinates center = new Coordinates("0","0");
    private int zoom = 1;


    public Backend() {
    }

    public Backend(String name, String displayName, String url) {
        this.name = name;
        this.displayName = displayName;
        Url = url;
    }

    public Backend(String name, String displayName, String url, Coordinates center, int zoom) {
        this.name = name;
        this.displayName = displayName;
        Url = url;
        this.center = center;
        this.zoom = zoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Coordinates getCenter() {
        return center;
    }

    public void setCenter(Coordinates center) {
        this.center = center;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    @Override
    public String toString() {
        return "Backend{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", Url='" + Url + '\'' +
                ", center='" + center + '\'' +
                ", zoom='" + zoom + '\'' +
                '}';
    }
}
