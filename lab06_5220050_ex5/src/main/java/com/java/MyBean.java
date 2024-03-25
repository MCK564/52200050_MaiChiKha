package com.java;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class MyBean {
    @Value("${my.property}")
    private String myProperty;

    public String getMyProperty() {
        return myProperty;
    }
}
