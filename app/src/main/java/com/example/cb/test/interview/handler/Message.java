package com.example.cb.test.interview.handler;

import androidx.annotation.NonNull;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/11 22:52
 * @Desc :
 * ====================================================
 */
public class Message {

    Handler target;
    Object object;

    public Message(Object o) {
        object = o;
    }

    @NonNull
    @Override
    public String toString() {
        return object.toString();
    }
}
