package com.openshift.evangelists.roadshow.db;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.openshift.evangelists.roadshow.model.Coordinates;
import com.openshift.evangelists.roadshow.model.DataPoint;
import com.openshift.evangelists.roadshow.model.NationalPark;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmorales on 11/08/16.
 */
public class MongoDBConnection {

    private static final String FILENAME = "/resources/national-parks-coordinates-all.json";

    private static final String COLLECTION = "nationalparks";

    String dbHost = System.getenv("DB_HOST");
    String dbPort = System.getenv("DB_PORT");
    String dbUsername = System.getenv("DB_USERNAME");
    String dbPassword = System.getenv("DB_PASSWORD");
    String dbName = System.getenv("DB_NAME");

    public MongoDatabase connect(){
        List<MongoCredential> creds = new ArrayList<MongoCredential>();
        creds.add(MongoCredential.createCredential(dbUsername,dbName,dbPassword.toCharArray()));

        MongoClient mongoClient = new MongoClient(new ServerAddress(dbHost, Integer.valueOf(dbPort)), creds);
        MongoDatabase database = mongoClient.getDatabase(dbName);

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
        int i = 1;

        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        try {
            while ((currentLine = in.readLine()) != null) {
                String s = currentLine.toString();
                System.out.println("line "+ i++ + ": " + s);
                Document doc = Document.parse(s);
                docs.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docs;
    }


    /**
     *
     * @param database
     */
    public void clear(MongoDatabase database) {
        MongoCollection<Document> collection = this.getCollection(database);
        collection.drop();
    }

    /**
     *
     * @param database
     * @return
     */
    public MongoCollection<Document> getCollection(MongoDatabase database){
        return database.getCollection(COLLECTION);
    }

    /**
     *
     * @param database
     * @param parks
     */
    public void init(MongoDatabase database, List<Document> parks) {
        MongoCollection<Document> collection = this.getCollection(database);

        System.out.println("Items before insert: " + collection.count());
        if (collection.count()!=0){
            collection.drop();
            System.out.println("Items droped");
            System.out.println("Items after drop: " + collection.count());
        }
        collection.insertMany(parks);
        collection.createIndex( new Document("coordinates","2d") );
        System.out.println("Items after insert: " + collection.count());
    }

    /**
     *
     * @param database
     * @return
     */
    public long sizeInDB(MongoDatabase database) {
        MongoCollection<Document> collection = this.getCollection(database);
        return collection.count();
    }

    /**
     *
     * @param database
     * @param parks
     */
    public void insert(MongoDatabase database, List<Document> parks) {
        MongoCollection<Document> collection = this.getCollection(database);

        System.out.println("Items before insert: " + collection.count());
        collection.insertMany(parks);
        System.out.println("Items after insert: " + collection.count());
    }

    /**
     *
     * @param database
     * @return
     */
    public List<DataPoint> getAll(MongoDatabase database){
        int i = 0;
        FindIterable<Document> iterable = this.getCollection(database).find();
        List<DataPoint> dataPoints = new ArrayList<DataPoint>();
        for(Document current: iterable){
            DataPoint dataPoint = getPark(current);
            System.out.println("Adding item " + i++ + ": " + dataPoint);
            dataPoints.add(dataPoint);
        }
        return dataPoints;
    }

    public List<DataPoint> getByQuery(MongoDatabase database, BasicDBObject query){
        int i=0;
        FindIterable<Document> iterable = this.getCollection(database).find(query);
        List<DataPoint> dataPoints = new ArrayList<DataPoint>();
        for(Document current: iterable){
            DataPoint dataPoint = getPark(current);
            System.out.println("Adding item " + i++ + ": " + dataPoint);
            dataPoints.add(dataPoint);
        }
        return dataPoints;
    }

    /**
     *
     * @param current
     * @return
     */
    public DataPoint getPark(Document current){
        NationalPark park = new NationalPark();
        park.setId(current.getObjectId("_id").toString());
        park.setName(current.getString("name"));
        park.setToponymName(current.getString("toponymName"));

        Coordinates cord = new Coordinates(current.get("coordinates",List.class));
        park.setPosition(cord);
        park.setLatitude(cord.getLatitude());
        park.setLongitude(cord.getLongitude());

        park.setCountryCode(current.getString("countryCode"));
        park.setCountryName(current.getString("countryName"));
        return park;
    }


    public static void main(String[] args) {
        MongoDBConnection con = new MongoDBConnection();
//        List<Document> parks = con.loadParks("/Users/jmorales/repositories/jorgemoralespou/openshift/roadshow-mongodb/nationalparks-mongo/src/main/resources/national-parks-coordinates-all.json");
        List<Document> parks = con.loadParks("/Users/jmorales/repositories/jorgemoralespou/openshift/roadshow-mongodb/nationalparks-mongo/src/main/resources/national-parks-coordinates-all.json");
        MongoDatabase db = con.connect();
        con.init(db, parks);
        System.out.println("Number of national parks in the DB: " + con.sizeInDB(db));

        System.out.println("Get list of parks in DB");
        List<DataPoint> dataPointList = con.getAll(db);
        for (DataPoint dataPoint : dataPointList){
            System.out.println("DataPoint: " + dataPoint.toString());
        }
    }



}
