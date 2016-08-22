package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.model.DataPoint;
import com.openshift.evangelists.roadshow.model.DefaultDataPoint;
import com.openshift.evangelists.roadshow.model.View;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DefaultView implements ViewResource {

    private View exampleView = new View("Example map on OpenShift 3", "39.82", "-98.57", 5);

    String remoteService = System.getenv("DATA_SERVICE");

    Client client = ClientBuilder.newClient();

    @Override
    public View getInitialView() {
        System.out.println("[DEBUG] getInitialView");

        if (remoteService != null) {
            // TODO: Set a timeout of 1 second, at most
            Response res = client.target("http://" + remoteService + "/ws/view/initialview").request(MediaType.APPLICATION_JSON).get();
            if (res.getStatus() == 200) {
                System.out.println("[INFO] Remote service responded ok: " + res);
                return (View) res.readEntity(View.class);
            } else {
                System.out.println("[INFO] Remote service not available, using defaults");
            }
        } else {
            System.out.println("[INFO] Remote service not specified, using defaults");
        }
        return exampleView;
    }
}
