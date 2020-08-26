package com.example.cb.test;

public class NativeClass {


    native void test1();
    native String test2();
    native int test3();
    native float test4();

    static {
        System.load("E:\\CPlae\\NativeTest\\cmake-build-debug\\libandroid_dongtai.dll");
    }

    public static void main(String[] args) {
        NativeClass register = new NativeClass();
        register.test1();
        System.out.println(register.test2());
        System.out.println(register.test3());
        System.out.println(register.test4());
    }

}