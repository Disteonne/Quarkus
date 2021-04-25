package com.nc.quarkus_test.guide_two.part_two.resource;

import com.nc.quarkus_test.guide_two.part_two.entity.Fruit;
import com.nc.quarkus_test.guide_two.part_two.service.FruitService;
import com.nc.quarkus_test.guide_two.part_two.service_impl.FruitServiceImpl;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Inject
    private PgPool client;
    @Inject

    private FruitServiceImpl fruitService;

    @GET
    public Multi<Fruit> getAll(){
        return fruitService.findAll(client);
    }

    @GET()
    @Path("/{id}")
    public Uni<Fruit> getId(@PathParam("id") Long id){
        return fruitService.findById(client,id);
    }

    @POST
    public Uni<Response> create(Fruit fruit){
        return fruitService.save(client,fruit.getName())
                .onItem().transform(fruit1 -> URI.create("/fruits"+fruit1.getId()))
                .onItem().transform(uri -> Response.created(uri).build());
    }

    @PATCH
    @Path("/update/{id}")
    public Uni<Response> update(@PathParam("id") Long id,Fruit fruit){
        return fruitService.update(client,fruit.getName(),id)
                .onItem().transform(aBoolean -> aBoolean ? Response.Status.OK : Response.Status.NOT_FOUND)
                .onItem().transform(status-> Response.status(status).build());
    }

    @DELETE
    @Path("/delete/{id}")
    public Uni<Fruit> delete(@PathParam("id") Long id){
        return fruitService.delete(client,id);
    }
}
