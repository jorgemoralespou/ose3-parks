package com.openshift.evangelists.roadshow.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.openshift.evangelists.roadshow.model.Coordinates;
import com.openshift.evangelists.roadshow.model.MLBPark;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmorales on 11/08/16.
 */
public class MongoDBConnection {

    private static final String FILENAME = "/resources/mlbparks.json";

    private static final String COLLECTION = "mlbparks";

    // TODO: Get this from a Config file
    String mongoHost = System.getenv("MONGODB_SERVICE_HOST");
    String mongoPort = System.getenv("MONGODB_SERVICE_PORT");
    String mongoUser = System.getenv("MONGODB_USER");
    String mongoPassword = System.getenv("MONGODB_PASSWORD");
    String mongoDBName = System.getenv("MONGODB_DATABASE");

    public MongoDatabase connect(){
        List<MongoCredential> creds = new ArrayList<MongoCredential>();
        creds.add(MongoCredential.createCredential("mongodb","mongodb","mongodb".toCharArray()));

        MongoClient mongoClient = new MongoClient(new ServerAddress("10.2.2.2", 30519), creds);
        MongoDatabase database = mongoClient.getDatabase("mongodb");

        return database;
    }

    /*
     * Load from embedded list of parks using FILENAME
     */
    public List<Document>  loadParks(){
        List<Document> docs = new ArrayList<Document>();

        try {
            docs.addAll(loadParks(getClass().getClassLoader().getResourceAsStream(FILENAME)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docs;
    }


    public List<Document>  loadParks(String fileLocation){
        List<Document> docs = new ArrayList<Document>();
        try {
            docs.addAll(loadParks(new FileInputStream(new File(fileLocation))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docs;
    }

    public List<Document> loadParks(InputStream is){
        List<Document> docs = new ArrayList<Document>();
        String currentLine = null;

        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        try {
            while ((currentLine = in.readLine()) != null) {
                docs.add(Document.parse(currentLine.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docs;
    }


    public void clear(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection(COLLECTION);
        collection.drop();
    }

    public void init(MongoDatabase database, List<Document> parks) {
        MongoCollection<Document> collection = database.getCollection(COLLECTION);

        System.out.println("Items before insert: " + collection.count());
        if (collection.count()!=0){
            collection.drop();
            System.out.println("Items droped");
            System.out.println("Items after drop: " + collection.count());
        }
        collection.insertMany(parks);
        System.out.println("Items after insert: " + collection.count());
    }

    public long sizeInDB(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection(COLLECTION);
        return collection.count();
    }

    public void insert(MongoDatabase database, List<Document> parks) {
        MongoCollection<Document> collection = database.getCollection(COLLECTION);

        System.out.println("Items before insert: " + collection.count());
        collection.insertMany(parks);
        System.out.println("Items after insert: " + collection.count());
    }

    public List<MLBPark> getAll(MongoDatabase database){
        FindIterable<Document> iterable = database.getCollection(COLLECTION).find();
        List<MLBPark> parks = new ArrayList<MLBPark>();
        for(Document current: iterable){
            MLBPark park = new MLBPark();
            park.setId(current.getObjectId("_id").toString());
            park.setName(current.getString("name"));
            park.setBallpark(current.getString("ballpark"));

            Coordinates cord = new Coordinates(current.get("coordinates",List.class));
            park.setPosition(cord);
            park.setLatitude(cord.getLatitude());
            park.setLongitude(cord.getLongitude());

            park.setLeague(current.getString("league"));
            park.setPayroll(current.getInteger("payroll"));

            parks.add(park);
        }
        return parks;
    }





    public static void main(String[] args) {
        MongoDBConnection con = new MongoDBConnection();
        List<Document> parks = con.loadParks("/Users/jmorales/repositories/jorgemoralespou/openshift/roadshow-mongodb/mlbparks-mongo/src/main/resources/mlbparks.json");
        MongoDatabase db = con.connect();
        con.init(db, parks);
        System.out.println("Number of national parks in the DB: " + con.sizeInDB(db));

        System.out.println("Get list of parks in DB");
        List<MLBPark> parkList = con.getAll(db);
        for (MLBPark park: parkList){
            System.out.println("Park: " + park.toString());
        }
    }



}
