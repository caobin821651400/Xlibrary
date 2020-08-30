package jni1;

public class ClassMethod {

    public ClassMethod() {
        System.err.println("构造方法执行了");
    }

    /**
     * @param str
     * @param i
     */
    public static void callStaticMethod(String str, int i) {
        System.err.format("ClassMethod的callStaticMethod方法执行了-->str=%s," +
                "  i=%d\n", str, i);
    }

    /**
     * @param str
     * @param i
     */
    public void callNormalMethod(String str, int i) {
        System.err.format("ClassMethod的callNormalMethod方法执行了-->str=%s," +
                "  i=%d\n", str, i);
    }


    /**
     * @param str
     */
    public static Object callStaticObjectMethod(String str) {
        System.err.format("ClassMethod的callStaticObjectMethod方法执行了-->str=%s",str);
        return 100;
    }
}