package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.model.DataPoint;
import com.openshift.evangelists.roadshow.model.DefaultDataPoint;

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
public class DefaultResource implements DataPointsResource {


    private ArrayList<DataPoint> exampleDataPointList = new ArrayList<DataPoint>();
    private ArrayList<DataPoint> emptyList = new ArrayList<DataPoint>();

    String remoteService = System.getenv("DATA_SERVICE");

    Client client = ClientBuilder.newClient();

    public DefaultResource() {
        exampleDataPointList.add(new DefaultDataPoint("1", "Example", "33.80003", "-117.883043"));
        exampleDataPointList.add(new DefaultDataPoint("1", "Example2", "40.446947", "-80.005666"));
    }

    public List<? extends DataPoint> getAllDataPoints() {
        System.out.println("[DEBUG] getAllDataPoints");

        if (remoteService != null) {
            Response res = client.target("http://" + remoteService + "/ws/data/").request(MediaType.APPLICATION_JSON).get();
            if (res.getStatus() == 200) {
                System.out.println("[INFO] Remote service responded ok: " + res);
                return (List<DefaultDataPoint>) res.readEntity(new GenericType<List<DefaultDataPoint>>() {
                });
            } else {
                System.out.println("[INFO] Remote service not available, using defaults");
            }
        } else {
            System.out.println("[INFO] Remote service not specified, using defaults");
        }
        return emptyList;
    }


    public List<? extends DataPoint> findDataPointsWithin(@QueryParam("lat1") float lat1,
                                                          @QueryParam("lon1") float lon1,
                                                          @QueryParam("lat2") float lat2,
                                                          @QueryParam("lon2") float lon2) {
        System.out.println("[DEBUG] findDataPointsWithin(" + lat1 + "," + lon1 + "," + lat2 + "," + lon2 + ")");

        if (remoteService != null) {
            Response res = client.target("http://" + remoteService + "/ws/data/within?lat1=" + lat1 + "&lon1=" + lon1 + "&lat2=" + lat2 + "&lon2=" + lon2).request(MediaType.APPLICATION_JSON).get();
            if (res.getStatus() == 200) {
                System.out.println("[INFO] Remote service responded ok: " + res);
                return (List<DefaultDataPoint>) res.readEntity(new GenericType<List<DefaultDataPoint>>() {
                });
            } else {
                System.out.println("[INFO] Remote service not available, using defaults");
            }
        } else {
            System.out.println("[INFO] Remote service not specified, using defaults");
        }
        return emptyList;
    }


    public List<DataPoint> findDataPointsCentered(float lat, float lon, int maxDistance, int minDistance) {
        // TODO: Implement this
        return null;
    }

}
