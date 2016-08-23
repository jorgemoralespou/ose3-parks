package com.openshift.evangelists.roadshow.rest;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.openshift.evangelists.roadshow.db.MongoDBConnection;
import com.openshift.evangelists.roadshow.model.DataPoint;
import com.openshift.evangelists.roadshow.model.View;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MLBParks implements DataPointsResource {

	MongoDBConnection con = new MongoDBConnection();

	//@Inject
	//private DBConnection dbConnection;

	@GET∫
	@Path("/load")
	public String load(){
		System.out.println("[INFO] load()");
		MongoDBConnection con = new MongoDBConnection();
		List<Document> parks = con.loadParks();
		MongoDatabase db = con.connect();
		con.init(db, parks);
		return "Items inserted in database: " + con.sizeInDB(db);
	}

	public List<? extends DataPoint> getAllDataPoints() {
		System.out.println("[DEBUG] getAllDataPoints");

		MongoDBConnection con = new MongoDBConnection();
		MongoDatabase db = con.connect();

		return con.getAll(db);
	}


	public List<? extends DataPoint> findDataPointsWithin(@QueryParam("lat1") float lat1,
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

		return con.getByQuery(db,spatialQuery);
	}

	@Override
	public List<DataPoint> findDataPointsCentered(float lat, float lon, int maxDistance, int minDistance) {
		// TODO: Implement this
		return null;
	}

}
