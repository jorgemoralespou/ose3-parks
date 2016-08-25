package com.openshift.evangelists.roadshow.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by jmorales on 24/08/16.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.openshift.evangelists.roadshow.rest,com.openshift.evangelists.roadshow.parks.rest")
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}