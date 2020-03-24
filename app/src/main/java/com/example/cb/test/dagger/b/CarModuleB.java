package com.example.cb.test.dagger.b;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 11:40
 * @Desc :Module
 * ====================================================
 */
@Module
public class CarModuleB {


    public CarModuleB() {
    }

    /**
     * 必须使用@Name注解来标注不同的Provider
     */

    @Named("provider1")
    @Provides
    EngineB providerEngineB1() {
        return new EngineB("我是参数");
    }

    @Named("provider2")
    @Provides
    EngineB providerEngineB2() {
        return new EngineB();
    }
}
