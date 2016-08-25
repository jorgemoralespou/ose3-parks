package com.openshift.evangelists.roadshow.parks.rest;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.openshift.evangelists.roadshow.parks.db.MongoDBConnection;
import com.openshift.evangelists.roadshow.parks.model.DataPoint;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/ws/data")
@RestController
public class Parks {

    MongoDBConnection con = new MongoDBConnection();

    //@Inject
    //private DBConnection dbConnection;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/load", produces = "application/json")
    public String load() {
        System.out.println("[INFO] load()");
        MongoDBConnection con = new MongoDBConnection();
        List<Document> parks = con.loadParks();
        MongoDatabase db = con.connect();
        con.init(db, parks);
        return "Items inserted in database: " + con.sizeInDB(db);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    public List<? extends DataPoint> getAllDataPoints() {
        System.out.println("[DEBUG] getAllDataPoints");

        MongoDBConnection con = new MongoDBConnection();
        MongoDatabase db = con.connect();

        return con.getAll(db);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/within", produces = "application/json")
    public List<? extends DataPoint> findDataPointsWithin(
            @RequestParam("lat1") float lat1,
            @RequestParam("lon1") float lon1,
            @RequestParam("lat2") float lat2,
            @RequestParam("lon2") float lon2) {
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

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/centered", produces = "application/json")
    public List<DataPoint> findDataPointsCentered(@RequestParam("lat") float lat, @RequestParam("lon") float lon, @RequestParam("maxDistance") int maxDistance, @RequestParam("minDistance") int minDistance) {


        // TODO: Implement this
        return null;
    }

}
