package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.model.DefaultPark;
import com.openshift.evangelists.roadshow.model.Park;
import com.openshift.evangelists.roadshow.model.View;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DefaultResource implements ParksResource {


    private ArrayList<Park> exampleParkList = new ArrayList<Park>();
    private ArrayList<Park> emptyList = new ArrayList<Park>();
    private View exampleView = new View("Example map on OpenShift 3", "39.82", "-98.57", 5);

    String parkService = System.getenv("PARKS_SERVICE");

    Client client = ClientBuilder.newClient();

    public DefaultResource() {
        exampleParkList.add(new DefaultPark("1", "Example", "33.80003", "-117.883043"));
        exampleParkList.add(new DefaultPark("1", "Example2", "40.446947", "-80.005666"));
    }

    public List<Park> getAllParks() {
        System.out.println("[DEBUG] getAllParks");

        if (parkService != null) {
            Response res = client.target("http://" + parkService + "/ws/parks/").request(MediaType.APPLICATION_JSON).get();
            if (res.getStatus() == 200) {
                System.out.println("[INFO] Remote service responded ok: " + res);
                return (List<Park>) res.readEntity(new GenericType<List<Park>>() {});
            } else {
                System.out.println("[INFO] Remote service not available, using defaults");
            }
        } else {
            System.out.println("[INFO] Remote service not specified, using defaults");
        }
        return emptyList;
    }


    public List<Park> findParksWithin(@QueryParam("lat1") float lat1,
                                      @QueryParam("lon1") float lon1,
                                      @QueryParam("lat2") float lat2,
                                      @QueryParam("lon2") float lon2) {
        System.out.println("[DEBUG] findParksWithin(" + lat1 + "," + lon1 + "," + lat2 + "," + lon2 + ")");

        if (parkService != null) {
            Response res = client.target("http://" + parkService + "/ws/parks/within").request(MediaType.APPLICATION_JSON).get();
            if (res.getStatus() == 200) {
                System.out.println("[INFO] Remote service responded ok: " + res);
                return (List<Park>) res.readEntity(new GenericType<List<Park>>() {});
            } else {
                System.out.println("[INFO] Remote service not available, using defaults");
            }
        } else {
            System.out.println("[INFO] Remote service not specified, using defaults");
        }
        return emptyList;
    }

    @Override
    public View getInitialView() {
        System.out.println("[DEBUG] getInitialView");

        if (parkService != null) {
            Response res = client.target("http://" + parkService + "/ws/parks/initialView").request(MediaType.APPLICATION_JSON).get();
            if (res.getStatus() == 200) {
                System.out.println("[INFO] Remote service responded ok: " + res);
                return (View) res.readEntity(View.class);
            } else {
                System.out.println("[INFO] Remote service not available, using defaults");
            }
        } else {
            System.out.println("[INFO] Remote service not specified, using defaults");
        }
        return exampleView;
    }
}
