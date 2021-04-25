package com.nc.quarkus_test.guide_two.part_two.service;

import com.nc.quarkus_test.guide_two.part_two.entity.Fruit;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;


public interface FruitService {

    Multi<Fruit> findAll(PgPool client);

    Uni<Fruit> findById(PgPool client, Long id);

    Uni<Fruit> save(PgPool client,String name);

    Uni<Fruit> delete(PgPool client,Long id);

    Uni<Boolean> update(PgPool client,String name,Long id);


}
