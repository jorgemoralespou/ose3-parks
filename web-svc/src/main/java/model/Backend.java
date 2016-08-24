package model;

/**
 * Created by jmorales on 24/08/16.
 */
public class Backend {
    private String name;
    private String displayName;
    private String Url;


    public Backend() {
    }

    public Backend(String name, String displayName, String url) {
        this.name = name;
        this.displayName = displayName;
        Url = url;
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

    @Override
    public String toString() {
        return "Backend{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
