package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.parks.model.Coordinates;
import model.Backend;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmorales on 24/08/16.
 */
@RestController
@RequestMapping("/ws/backends")
public class BackendsController {


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/", produces = "application/json")
    public void register(Backend backend) {
        System.out.println("[INFO] Backends.register(" + backend + ")");
        //
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, value = "/", produces = "application/json")
    public void delete(Backend backend) {
        System.out.println("[INFO] Backends.delete(" + backend + ")");
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    public List<Backend> getAll() {
        System.out.println("[INFO] Backends: getAll");

        List<Backend> backends = new ArrayList<Backend>();

        backends.add(new Backend("parks", "MLB Parks", "http://localhost:8081"));
        backends.add(new Backend("nationalparks", "National Parks", "", new Coordinates("39.82", "-98.57"), 5));

        return backends;
    }

}
