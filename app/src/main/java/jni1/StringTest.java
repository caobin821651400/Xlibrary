package jni1;

public class StringTest {

    static {
        System.load("E:\\CPlae\\NativeTest\\cmake-build-debug\\libstringLib.dll");
    }

    public native static String sayHello(String txt);

    public static void main(String[] args) {
        String txt = sayHello("张三");
        System.err.println(txt);
    }

}