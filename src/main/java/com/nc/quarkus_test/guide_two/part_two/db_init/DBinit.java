package com.nc.quarkus_test.guide_two.part_two.db_init;

import io.vertx.mutiny.pgclient.PgPool;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DBinit {

    @Inject
    private PgPool client;

    @PostConstruct
    void configDB(){
        initdb();
    }

    private void initdb(){
        client.query("DROP  TABLE  IF EXISTS  fruits")
                .execute()
                .flatMap(m->client.query("CREATE  TABLE fruits (id SERIAL PRIMARY KEY ," +
                        "name VARCHAR(30) not null )")
                        .execute())
                .flatMap(m-> client.query("INSERT INTO fruits (name) VALUES ('Kiwi')").execute())
                .flatMap(m-> client.query("INSERT INTO fruits (name) VALUES ('Banana')").execute())
                .flatMap(m-> client.query("INSERT INTO fruits (name) VALUES ('Pomelo')").execute())
                .await().indefinitely();
    }
}
