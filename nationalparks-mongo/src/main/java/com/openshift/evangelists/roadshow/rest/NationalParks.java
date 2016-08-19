package com.openshift.evangelists.roadshow.rest;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.MongoDatabase;
import com.openshift.evangelists.roadshow.db.MongoDBConnection;
import com.openshift.evangelists.roadshow.model.NationalPark;
import com.openshift.evangelists.roadshow.model.Park;
import com.openshift.evangelists.roadshow.model.View;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmorales on 11/08/16.
 */
public class NationalParks implements ParksResource{

    @GET
    @Produces("text/json")
    @Path("/load")
    public String load(){
        MongoDBConnection con = new MongoDBConnection();
        List<Document> parks = con.loadParks("/Users/jmorales/repositories/jorgemoralespou/openshift/roadshow-mongodb/src/main/rest/national-parks-all.json");
        MongoDatabase db = con.connect();
        con.init(db, parks);
        return "Items inserted in database: " + con.sizeInDB(db);
    }

    @DELETE
    @Produces("text/json")
    public String delete(){
        MongoDBConnection con = new MongoDBConnection();
        MongoDatabase db = con.connect();
        con.clear(db);
        return "Deleted: Items in database: " + con.sizeInDB(db);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        MongoDBConnection con = new MongoDBConnection();
        MongoDatabase db = con.connect();
        List<NationalPark> parks = con.getAll(db);
        return Response.ok(parks).build();
    }





    public List<Park> getAllParks() {
        ArrayList<Park> allParksList = new ArrayList<Park>();

    /*
        DBCollection mlbParks = this.getMLBParksCollection();
        DBCursor cursor = mlbParks.find();
        try {
            while (cursor.hasNext()) {
                allParksList.add(this.populateParkInformation(cursor.next()));
            }
        } finally {
            cursor.close();
        }
    */
        return allParksList;
    }

    public List<Park> findParksWithin(@QueryParam("lat1") float lat1,
                                      @QueryParam("lon1") float lon1,
                                      @QueryParam("lat2") float lat2,
                                      @QueryParam("lon2") float lon2) {

        ArrayList<Park> allParksList = new ArrayList<Park>();
        /*
        DBCollection mlbParks = this.getMLBParksCollection();

        // make the query object
        BasicDBObject spatialQuery = new BasicDBObject();

        ArrayList<double[]> boxList = new ArrayList<double[]>();
        boxList.add(new double[] { new Float(lon2), new Float(lat2) });
        boxList.add(new double[] { new Float(lon1), new Float(lat1) });

        BasicDBObject boxQuery = new BasicDBObject();
        boxQuery.put("$box", boxList);

        spatialQuery.put("coordinates", new BasicDBObject("$within", boxQuery));
        System.out.println("Using spatial query: " + spatialQuery.toString());

        DBCursor cursor = mlbParks.find(spatialQuery);
        try {
            while (cursor.hasNext()) {
                allParksList.add(this.populateParkInformation(cursor.next()));
            }
        } finally {
            cursor.close();
        }
        */

        return allParksList;
    }

    @Override
    public View getInitialView() {
        return new View("National Parks on OpenShift 3", "39.82","-98.57", 2);
    }
}
