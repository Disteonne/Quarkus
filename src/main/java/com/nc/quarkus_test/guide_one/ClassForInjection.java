package com.nc.quarkus_test.guide_one;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClassForInjection {
    public String sayHello(String name){
        return "Say hello,dear "+name;
    }
}
