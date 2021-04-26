package com.nc.quarkus_test.core.configuration;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/")
public class ConfigurationResource {

    @ConfigProperty(name = "greeting.message")
    private String message;

    @ConfigProperty(name = "greeting.name")
    private String name;

    @ConfigProperty(name = "greeting.char",defaultValue = "!")
    private String chars;

    @ConfigProperty(name = "greeting.absent")
    private Optional<String> absent;

    @GET
    @Path("/result")
    @Produces(MediaType.TEXT_PLAIN)
    public String result(){
        return message+" , "+name+ " "+chars +" " +absent.orElse("Value is absent");
    }
}
