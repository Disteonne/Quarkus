package com.nc.quarkus_test.start.guide_two.part_one;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;

@ApplicationScoped
public class ReactiveGreetingService {

    public Uni<String> greeting(String name){
        return Uni.createFrom().item(name).onItem().transform(n-> String.format("Hello %s",n));
    }

    public Multi<String> greetings(int count,String name){
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().transform(n->String.format("Hellp %s - %d",name,n))
                .transform().byTakingFirstItems(count);
    }
}
