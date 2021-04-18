package com.example.app.javalibrary.design.proxy;
/**
 * ====================================================
 * @User :caobin
 * @Date :2021/4/18 21:47
 * @Desc :代理对象
 * ====================================================
 */
public class HttpProxy implements HttpRequest {
    private HttpRequest httpRequest;
    public HttpProxy(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
    @Override
    public String get() {
        return httpRequest.get();
    }
}