package com.openshift.evangelists.roadshow.rest;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.openshift.evangelists.roadshow.db.MongoDBConnection;
import com.openshift.evangelists.roadshow.model.DataPoint;
import com.openshift.evangelists.roadshow.model.View;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MLBParksView implements ViewResource {

	@Override
	public View getInitialView() {
		System.out.println("[DEBUG] getInitialView");

		return new View("MLBParks on OpenShift 3", "39.82","-98.57", 5);
	}
}
