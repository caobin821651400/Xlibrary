package com.example.cb.test.dagger;

import javax.inject.Inject;

/**
 * Created by bin on 2019/1/21.
 */
public class AObject {

    @Inject
    public AObject() {
    }

    public void eat() {
        System.err.println("我是A");
    }
}
