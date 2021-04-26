package com.nc.quarkus_test.guide_two.part_two.service_impl;

import com.nc.quarkus_test.guide_two.part_two.entity.Fruit;
import com.nc.quarkus_test.guide_two.part_two.service.FruitService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class FruitServiceImpl implements FruitService {
    @Override
    public Multi<Fruit> findAll(PgPool client) {
        return  client.query("SELECT * FROM fruits ORDER BY id ASC").execute()
                .onItem().transformToMulti(set->Multi.createFrom().items(()-> StreamSupport.stream(set.spliterator(),false)))
                .onItem().transform(FruitServiceImpl::from);
    }

    @Override
    public Uni<Fruit> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT id, name FROM fruits WHERE id = $1").execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator->iterator.hasNext() ? from(iterator.next()):null);
    }

    @Override
    public Uni<Fruit> save(PgPool client,String name) {
        return client.preparedQuery("INSERT INTO  fruits (name) VALUES ($1) returning (id)")
                .execute(Tuple.of(name))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator->iterator.hasNext() ? from(iterator.next(),name):null);
    }

    @Override
    public Uni<Fruit> delete(PgPool client,Long id) {
        return client.preparedQuery("DELETE  FROM fruits WHERE id=$1")
                .execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator-> iterator.hasNext() ? from(iterator.next()): null);
    }

    @Override
    public Uni<Boolean> update(PgPool client,String name,Long id) {
        return client.preparedQuery("UPDATE fruits SET name = $1 WHERE id= $2")
                .execute(Tuple.of(name,id))
                .onItem().transform(pgRowSet->pgRowSet.rowCount()==1);
    }

    public static Fruit from(Row row) {
        return new Fruit(row.getLong("id"),row.getString("name"));
    }

    public static Fruit from(Row row,String name){
        return new Fruit(row.getLong("id"),name);
    }
}
