package com.example.app.javalibrary.design.proxy;

public class ProxyTest {
    public static void main(String[] args) {
        XVolley volley = new XVolley();
        XOkHttp okHttp = new XOkHttp();
        HttpProxy proxy = new HttpProxy(volley);
        System.err.println(proxy.get());
        HttpProxy proxy2 = new HttpProxy(okHttp);
        System.err.println(proxy2.get());
    }

}