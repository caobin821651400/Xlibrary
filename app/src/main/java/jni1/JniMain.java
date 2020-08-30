package jni1;

public class JniMain {


    static {
        System.load("E:\\CPlae\\NativeTest\\cmake-build-debug\\libmethod.dll");
    }

    public native void callNormalMethod();

    public native void callStaticMethod();

    public native Object callStaticObjectMethod();

    public static void main(String[] args) {
        JniMain jniMain = new JniMain();

//        jniMain.callNormalMethod();
//        jniMain.callStaticMethod();
        jniMain.callStaticObjectMethod();
    }
}