package com.openshift.evangelists.roadshow.rest;

import com.mongodb.*;
import com.openshift.evangelists.roadshow.db.DBConnection;
import com.openshift.evangelists.roadshow.model.MLBPark;
import com.openshift.evangelists.roadshow.model.Park;
import com.openshift.evangelists.roadshow.model.View;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MLBParkResource implements ParksResource{

	@Inject
	private DBConnection dbConnection;
/*
	private DBCollection getMLBParksCollection() {
		DB db = dbConnection.getDB();
		DBCollection parkListCollection = db.getCollection("teams");

		return parkListCollection;
	}

	private MLBPark populateParkInformation(DBObject dataValue) {
		MLBPark thePark = new MLBPark();
		thePark.setName(dataValue.get("name"));
		thePark.setPosition(dataValue.get("coordinates"));
		thePark.setId(dataValue.get("_id").toString());
		thePark.setBallpark(dataValue.get("ballpark"));
		thePark.setLeague(dataValue.get("league"));
		thePark.setPayroll(dataValue.get("payroll"));

		return thePark;
	}
*/
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
		return new View("Example map on OpenShift 3", "39.82","-98.57", 5);
	}
}
