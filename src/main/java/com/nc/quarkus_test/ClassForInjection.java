package com.nc.quarkus_test;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClassForInjection {
    public String sayHello(String name){
        return "Say hello,dear "+name;
    }
}
