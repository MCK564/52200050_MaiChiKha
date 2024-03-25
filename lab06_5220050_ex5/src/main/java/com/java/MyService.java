package com.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyService {
    private final MyBean myBean;

    @Autowired
    public MyService(MyBean myBean) {
        this.myBean = myBean;
    }

    public void printProperty() {
        System.out.println("My Property: " + myBean.getMyProperty());
    }
}