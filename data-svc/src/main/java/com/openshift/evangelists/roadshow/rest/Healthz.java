package com.openshift.evangelists.roadshow.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by jmorales on 11/08/16.
 */
@Path("/healtz")
public class Healthz {

    @GET
    @Produces("text/plain")
    public String healthz(){
        return "OK";
    }
}
