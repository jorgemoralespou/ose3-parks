package com.openshift.evangelists.roadshow.rest;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.openshift.evangelists.roadshow.db.MongoDBConnection;
import com.openshift.evangelists.roadshow.model.DataPoint;
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
@ApplicationScoped
public class NationalParks implements DataPointsResource {

    MongoDBConnection con = new MongoDBConnection();

    @GET
    @Path("/load")
    public String load(){
        System.out.println("[INFO] load()");
        MongoDBConnection con = new MongoDBConnection();
        List<Document> parks = con.loadParks();
        MongoDatabase db = con.connect();
        con.init(db, parks);
        return "Items inserted in database: " + con.sizeInDB(db);
    }

    @DELETE
    @Produces("text/json")
    public String delete(){
        System.out.println("[INFO] delete()");
        MongoDBConnection con = new MongoDBConnection();
        MongoDatabase db = con.connect();
        con.clear(db);
        return "Deleted: Items in database: " + con.sizeInDB(db);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        System.out.println("[INFO] getAll()");
        MongoDBConnection con = new MongoDBConnection();
        MongoDatabase db = con.connect();
        List<DataPoint> dataPoints = con.getAll(db);
        return Response.ok(dataPoints).build();
    }



    public List<DataPoint> getAllDataPoints() {
        System.out.println("[DEBUG] getAllDataPoints");

        MongoDBConnection con = new MongoDBConnection();
        MongoDatabase db = con.connect();

        return con.getAll(db);
    }

    public List<DataPoint> findDataPointsWithin(@QueryParam("lat1") float lat1,
                                           @QueryParam("lon1") float lon1,
                                           @QueryParam("lat2") float lat2,
                                           @QueryParam("lon2") float lon2) {
        System.out.println("[DEBUG] findDataPointsWithin(" + lat1 + "," + lon1 + "," + lat2 + "," + lon2 + ")");

        MongoDBConnection con = new MongoDBConnection();
        MongoDatabase db = con.connect();

        // make the query object
        BasicDBObject spatialQuery = new BasicDBObject();

        ArrayList<double[]> boxList = new ArrayList<double[]>();
        boxList.add(new double[] { new Float(lat1), new Float(lon1) });
        boxList.add(new double[] { new Float(lat2), new Float(lon2) });

        BasicDBObject boxQuery = new BasicDBObject();
        boxQuery.put("$box", boxList);

        spatialQuery.put("coordinates", new BasicDBObject("$within", boxQuery));
        System.out.println("Using spatial query: " + spatialQuery.toString());

        return con.getByQuery(db, spatialQuery);
    }

    @Override
    public List<DataPoint> findDataPointsCentered(float lat, float lon, int maxDistance, int minDistance) {
        // TODO: Implement
        /*
                MongoDBConnection con = new MongoDBConnection();
        MongoDatabase db = con.connect();

        // make the query object
        BasicDBObject spatialQuery = new BasicDBObject();

        ArrayList<double[]> boxList = new ArrayList<double[]>();
        boxList.add(new double[] { new Float(lat1), new Float(lon1) });
        boxList.add(new double[] { new Float(lat2), new Float(lon2) });

        double [] center = new double[] { new Float(lat0), new Float(lon0) };


        DBObject nearQuery = BasicDBObjectBuilder.start().push("location")
                .push("$near")
                .add("$maxDistance", 100*1000)
                .push("$geometry")
                .add("type", "Point")
                .add("coordinates", center)
                .get();


        BasicDBObject boxQuery = new BasicDBObject();
        boxQuery.put("$box", boxList);

        spatialQuery.put("coordinates", new BasicDBObject("$within", boxQuery));
        //spatialQuery.put("coordinates", new BasicDBObject("$near", nearQuery));
        System.out.println("Using spatial query: " + nearQuery.toString());

        return con.getByQuery(db, spatialQuery);
//        return con.getByQuery(db,new BasicDBObject("$near",center));
//        return con.getByQuery(db,(BasicDBObject)nearQuery);
         */
        return null;
    }

}
