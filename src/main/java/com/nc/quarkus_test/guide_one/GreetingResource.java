package com.nc.quarkus_test.guide_one;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-resteasy")
public class GreetingResource {

    @Inject
    private ClassForInjection injection;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return injection.sayHello("Diana");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{date}")
    public String helloWithDate(@PathParam("date") String date){
        return  injection.sayHello("Diana,date: "+date);
    }
}