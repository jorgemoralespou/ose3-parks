package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.model.DataPoint;
import com.openshift.evangelists.roadshow.model.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by jmorales on 18/08/16.
 */
@Path("/data")
public interface DataPointsResource {

    @GET
    @Path("/load")
    public String load();

    /**
     *
     * @return
     */
    @GET()
    @Produces("application/json")
    public List<? extends DataPoint> getAllDataPoints();

    /**
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("within")
    public List<? extends DataPoint> findDataPointsWithin(@QueryParam("lat1") float lat1,
                                                     @QueryParam("lon1") float lon1,
                                                     @QueryParam("lat2") float lat2,
                                                     @QueryParam("lon2") float lon2);

    /**
     *
     * @param lat
     * @param lon
     * @param maxDistance
     * @param minDistance
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("centered")
    public List<DataPoint> findDataPointsCentered(@QueryParam("lat") float lat,
                                             @QueryParam("lon") float lon,
                                             @QueryParam("maxDistance") int maxDistance,
                                             @QueryParam("minDistance") int minDistance);



}
