package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.model.Park;
import com.openshift.evangelists.roadshow.model.View;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jmorales on 18/08/16.
 */
@Path("/parks")
public interface ParksResource {

    @GET()
    @Produces("application/json")
    public List<Park> getAllParks();

    @GET
    @Produces("application/json")
    @Path("within")
    public List<Park> findParksWithin(@QueryParam("lat1") float lat1,
                                      @QueryParam("lon1") float lon1,
                                      @QueryParam("lat2") float lat2,
                                      @QueryParam("lon2") float lon2);


    @GET
    @Produces("application/json")
    @Path("initialview")
    public View getInitialView();

}
