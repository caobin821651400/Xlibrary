package com.example.cb.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 15:39
 * @Desc :注解使用
 * ====================================================
 */
@AnnotationTest.Test2()
public class AnnotationTest {

    //-->1.注解通过 @interface关键字进行定义。
    //public @interface Test {
    //}

    //-->2.使用注解
    //@Test
    //public class TestAnnotation {
    //}

    //-->3.元注解,可以理解为注解的注解
    //元标签有 @Retention、@Documented、@Target、@Inherited、@Repeatable 5 种。
    // 3-1:@Retention 当 @Retention 应用到一个注解上的时候，它解释说明了这个注解的的存活时间。
    //     a:RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。
    //     b:RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中。
    //     c:RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们

    // 3-2:@Target  是目标的意思，@Target 指定了注解运用的地方
    //     a:1. ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
    //     b:2. ElementType.CONSTRUCTOR 可以给构造方法进行注解
    //     c:3. ElementType.FIELD 可以给属性进行注解
    //     d:4. ElementType.LOCAL_VARIABLE 可以给局部变量进行注解
    //     e:5. ElementType.METHOD 可以给方法进行注解
    //     f:6. ElementType.PACKAGE 可以给一个包进行注解
    //     g:7. ElementType.PARAMETER 可以给一个方法内的参数进行注解

    // 3-3:@Documented  它的作用是能够将注解中的元素包含到 Javadoc 中去。ElementType.TYPE 可以给一个类型进行注解，比如类、接口、枚举

    // 3-4:@Inherited  是继承的意思，但是它并不是说注解本身可以继承，而是说如果一个超类被 @Inherited 注解过的注解进行注解的话，
    //     那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。(子类默认集成父类的注解)

    // 3-5:@Repeatable 自然是可重复的意思。@Repeatable 是 Java 1.8 才加进来的，所以算是一个新的特性。
    //     什么样的注解会多次应用呢？通常是注解的值可以同时取多个。


    //-->4.注解的属性
    //     注意：注解的属性也叫做成员变量。注解只有成员变量，没有方法。
    //          需要注意的是，在注解中定义属性时它的类型必须是 8 种基本数据类型外加 类、接口、注解及它们的数组
    //          注解中属性可以有默认值，默认值需要用 default 关键值指定

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Test2 {
        int id() default -1;

        String msg() default "Hello";
    }

    //注解赋值
//    @Test2(id = 1, msg = "hello annotation")
//    public class TestAnnotation2 {
//    }


    //-->5.注解的提取
    //注解与反射。
    //5-1.注解通过反射获取。首先可以通过 Class 对象的 isAnnotationPresent() 方法判断它是否应用了某个注解
    //    //public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {}

    //5-2.然后通过 getAnnotation() 方法来获取 Annotation 对象
    //public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {}
    //或者是 getAnnotations() 方法。
    //public Annotation[] getAnnotations() {}
    //前一种方法返回指定类型的注解，后一种方法返回注解到这个元素上的所有注解。
    //如果获取到的 Annotation 如果不为 null，则就可以调用它们的属性方法了。比如
    public static void main(String[] args) {
        boolean hasAnnotation = AnnotationTest.class.isAnnotationPresent(Test2.class);
        //如果有注解
        if (hasAnnotation) {
            Test2 test2 = AnnotationTest.class.getAnnotation(Test2.class);
            System.out.println("id:" + test2.id());
            System.out.println("msg:" + test2.msg());
        }
    }


    //-->4.
    //-->5.
    //-->6.
    //-->7.
    //-->8.
    //-->9.
    //-->10.
    //-->1.
    //-->1.
    //-->1.
    //-->1.
    //-->1.
    //-->1.
    //-->1.
    //-->1.
    //-->1.
    //-->1.


}
