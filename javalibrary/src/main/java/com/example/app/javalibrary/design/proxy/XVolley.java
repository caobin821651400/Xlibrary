package com.example.app.javalibrary.design.proxy;

public class XVolley implements HttpRequest {

    @Override
    public String get() {
        //这里直接使用Volley去执行get请求
        return "通过Volley框架请求的结果";
    }
}