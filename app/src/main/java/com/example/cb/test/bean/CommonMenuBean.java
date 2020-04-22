package com.example.cb.test.bean;

public class CommonMenuBean {
    private String name;
    private Class<?> aClass;

    public CommonMenuBean(String name, Class<?> aClass) {
        this.name = name;
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }
}
