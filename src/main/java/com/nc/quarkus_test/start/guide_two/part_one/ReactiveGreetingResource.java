package com.nc.quarkus_test.start.guide_two.part_one;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ReactiveGreetingResource {

    @Inject
    private ReactiveGreetingService reactiveGreetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    public Uni<String> greeting(@PathParam("name") String name){
        return  reactiveGreetingService.greeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "Hello";
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Path("/greeting/{count}/{name}")
    public Multi<String> greeting(@PathParam("count") int count,@PathParam("name") String name){
        //@PathParam("count") int count,@PathParam("name") String name
        return  reactiveGreetingService.greetings(count,name);
    }
}
