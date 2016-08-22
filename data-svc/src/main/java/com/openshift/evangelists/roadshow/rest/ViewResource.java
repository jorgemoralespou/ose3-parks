package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.model.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by jmorales on 23/08/16.
 */
@Path("/view")
public interface ViewResource {

    @GET
    @Produces("application/json")
    @Path("initialview")
    public View getInitialView();
}
