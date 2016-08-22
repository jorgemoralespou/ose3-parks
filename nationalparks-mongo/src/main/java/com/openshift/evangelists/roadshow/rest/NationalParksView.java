package com.openshift.evangelists.roadshow.rest;

import com.openshift.evangelists.roadshow.model.View;

/**
 * Created by jmorales on 23/08/16.
 */
public class NationalParksView implements ViewResource {

    @Override
    public View getInitialView() {
        return new View("National Parks on OpenShift 3", "47.039304", "14.505178", 5);
    }

}
