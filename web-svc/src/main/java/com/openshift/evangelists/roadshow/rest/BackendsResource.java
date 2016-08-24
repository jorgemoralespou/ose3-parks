package com.openshift.evangelists.roadshow.rest;

import model.Backend;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmorales on 24/08/16.
 */
@ApplicationScoped
@Path("/backends")
public class BackendsResource {


    @POST
    @Path("/")
    public void register(Backend backend){
        //
    }

    @DELETE
    @Path("/")
    public void delete(Backend backend){
        //
    }

    @GET
    @Path("/")
    public List<Backend> getAll(){
        List<Backend> backends = new ArrayList<Backend>();

        backends.add(new Backend("mlbparks", "MLB Parks", "http://localhost:8081"));
        backends.add(new Backend("nationalparks", "National Parks", "http://localhost:8082"));

        return backends;
    }

}
