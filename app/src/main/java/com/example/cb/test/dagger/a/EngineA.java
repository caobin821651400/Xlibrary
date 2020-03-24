package com.example.cb.test.dagger.a;

import javax.inject.Inject;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 10:58
 * @Desc :引擎
 * ====================================================
 */
public class EngineA {

    /**
     * Dagger2通过@Inject注解在需要这个类的实例的时候，来找到这个类的构造方法并实例化出来
     * 以此来为被@Inject标记得变量提供依赖
     */
    @Inject
    public EngineA() {
    }

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                '}';
    }

    public void run() {
        System.out.println("引擎转起来了~~~");
    }

}
