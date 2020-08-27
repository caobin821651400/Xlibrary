package com.example.cb.test.native1;

public class FieldTest {


    public int a = 1;

    public static int b = 2;

    public native void testField();

    public native void testStaticField();

    static {
        System.load("E:\\CPlae\\NativeTest\\cmake-build-debug\\libfield.dll");
    }

    public static void main(String[] args) {
        FieldTest fieldTest = new FieldTest();
        fieldTest.testField();
        fieldTest.testStaticField();

        System.err.println("1->" + fieldTest.a);
        System.err.println("2->" + fieldTest.b);
    }
}