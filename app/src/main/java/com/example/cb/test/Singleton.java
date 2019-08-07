package com.example.cb.test;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/8/7 15:35
 * @Desc :静态内部类实现单例模式
 * ====================================================
 */
public class Singleton {

    private Singleton() {
    }

    public static Singleton getInstance() {
        return SingletonHolder.SINGLETON;
    }

    /**
     * 静态内部类
     */
    private static class SingletonHolder {
        private static final Singleton SINGLETON = new Singleton();
    }
}
