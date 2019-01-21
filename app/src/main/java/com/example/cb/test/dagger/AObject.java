package com.example.cb.test.dagger;

/**
 * Created by bin on 2019/1/21.
 */
public class AObject {

    BObject b;

    public AObject(BObject b) {
        this.b = b;
    }

    public void eat() {
        System.err.println(b.eat());
    }
}
