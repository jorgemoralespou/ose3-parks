package com.openshift.evangelists.roadshow.parks.db;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.openshift.evangelists.roadshow.parks.model.Coordinates;
import com.openshift.evangelists.roadshow.parks.model.DataPoint;
import com.openshift.evangelists.roadshow.parks.model.Park;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmorales on 11/08/16.
 */
@Component
public class MongoDBConnection {

    private static final String FILENAME = "/nationalparks.json";

    private static final String COLLECTION = "nationalparks";

    /*
    String dbHost = System.getenv("DB_HOST");
    String dbPort = System.getenv("DB_PORT");
    String dbUsername = System.getenv("DB_USERNAME");
    String dbPassword = System.getenv("DB_PASSWORD");
    String dbName = System.getenv("DB_NAME");

    public MongoDatabase connect(){
        System.out.println("[DEBUG] MongoDBConnection.connect()");

        List<MongoCredential> creds = new ArrayList<MongoCredential>();
        creds.add(MongoCredential.createCredential(dbUsername,dbName,dbPassword.toCharArray()));

        MongoClient mongoClient = new MongoClient(new ServerAddress(dbHost, Integer.valueOf(dbPort)), creds);
        MongoDatabase database = mongoClient.getDatabase(dbName);

        return database;
    }
    */

    @Autowired
    private ResourceLoader resourceLoader;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoDBConnection(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /*
     * Load from embedded list of parks using FILENAME
     */
    public List<Document>  loadParks(){
        System.out.println("[DEBUG] MongoDBConnection.loadParks()");

        try {
            return loadParks(new FileInputStream(resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + FILENAME).getFile()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading parks. Return empty list");
        }
        return new ArrayList<Document>();
    }


    public List<Document>  loadParks(String fileLocation){
        System.out.println("[DEBUG] MongoDBConnection.loadParks("+fileLocation+")");

        try {
            return loadParks(new FileInputStream(new File(fileLocation)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading parks. Return empty list");
        }
        return new ArrayList<Document>();
    }

    public List<Document> loadParks(InputStream is){
        System.out.println("[DEBUG] MongoDBConnection.loadParks(InputStream)");

        List<Document> docs = new ArrayList<Document>();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        try {
            String currentLine = null;
            int i = 1;
            while ((currentLine = in.readLine()) != null) {
                String s = currentLine.toString();
                // System.out.println("line "+ i++ + ": " + s);
                Document doc = Document.parse(s);
                docs.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading parks. Return empty list");
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading parks. Return empty list");
            }
        }
        return docs;
    }


    /**
     *
     */
    public void clear() {
        System.out.println("[DEBUG] MongoDBConnection.clear()");
        mongoTemplate.dropCollection(COLLECTION);
    }


    /**
     *
     * @param parks
     */
    public void init(List<Document> parks) {
        System.out.println("[DEBUG] MongoDBConnection.init(...)");

        mongoTemplate.dropCollection(COLLECTION);
        mongoTemplate.insert(parks, COLLECTION);
        mongoTemplate.getCollection(COLLECTION).createIndex(new BasicDBObject().append("coordinates", "2d"));
    }

    /**
     *
     * @return
     */
    public long sizeInDB() {
        return mongoTemplate.count(new Query(), COLLECTION);
    }

    /**
     *
     * @param parks
     */
    public void insert(List<Document> parks) {
        mongoTemplate.insert(parks, COLLECTION);
    }

    /**
     *
     * @return
     */
    public List<Park> getAll(){
        System.out.println("[DEBUG] MongoDBConnection.getAll()");

        return mongoTemplate.findAll(Park.class,COLLECTION);
    }

    /**
     *
     * @param query
     * @return
     */
    public List<Park> getByQuery(BasicDBObject query){
        System.out.println("[DEBUG] MongoDBConnection.getByQuery()");
        List<Park> parks = new ArrayList<Park>();
        ParkReadConverter converter = new ParkReadConverter();

        DBCursor cursor = mongoTemplate.getCollection(COLLECTION).find(query);
        int i = 0;
        while (cursor.hasNext()){
            Park park = converter.convert(cursor.next());
            // System.out.println("Adding item " + i++ + ": " + park);
            parks.add(park);
        }
        return parks;
    }

}
