package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.parks.model.Coordinates;
import model.Backend;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by jmorales on 24/08/16.
 */
@RestController
@RequestMapping("/ws/backends")
public class BackendsController {

    Map<String, Backend> backends = new HashMap<String, Backend>();

    BackendsController(){
        // This application for now will always be there.
        backends.put("nationalparks", new Backend("nationalparks", "National Parks", "", new Coordinates("39.82", "-98.57"), 5));
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/", produces = "application/json", consumes = "application/json")
    public List<Backend> register(@RequestBody Backend backend) {
        System.out.println("[INFO] Backends.register(" + backend + ")");
        //
        backends.put(backend.getName(), backend);

        return new ArrayList<Backend>(backends.values());
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, value = "/", produces = "application/json", consumes = "application/json")
    public List<Backend> delete(@RequestBody Backend backend) {

        System.out.println("[INFO] Backends.delete(" + backend + ")");
        backends.remove(backend.getName());

        return new ArrayList<Backend>(backends.values());
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/", produces = "application/json")
    public List<Backend> getAll() {
        System.out.println("[INFO] Backends: getAll");

//        List<Backend> backends = new ArrayList<Backend>();
//        backends.add(new Backend("parks", "MLB Parks", "http://localhost:8081"));
//        backends.add(new Backend("nationalparks", "National Parks", "", new Coordinates("39.82", "-98.57"), 5));

        return new ArrayList<Backend>(backends.values());
    }

}
