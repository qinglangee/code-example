package com.zhch.example.java.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@MyAnnotation(name = "class", value = "")
public class JR08Annotations {

    @MyAnnotation(name = "field01", value = "")
    private String field01;
    @MyAnnotation(name = "field2", value = "")
    public String field02;
    @MyAnnotation(name = "field3", value = "")
    public String field03;

    @MyAnnotation(name = "someName", value = "Hello World")
    public void doSomething(@MyAnnotation(name = "aName", value = "aValue") String arg) {
    }

    public void test() throws NoSuchMethodException, SecurityException, NoSuchFieldException {

        Class aClass = JR08Annotations.class;
        // 获取类的注解
        Annotation[] annotations = aClass.getAnnotations();

        for (Annotation annotation : annotations) {
            if (annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                print("name: " + myAnnotation.name(), "value: ", myAnnotation.value());
            }
        }

        // 获取指定的注解
        Annotation annotation = aClass.getAnnotation(MyAnnotation.class);

        if (annotation instanceof MyAnnotation) {
            MyAnnotation myAnnotation = (MyAnnotation) annotation;
            print("name: " + myAnnotation.name(), "value: ", myAnnotation.value());
        }

        // 获取方法上的注解
        Method method = aClass.getMethod("doSomething", new Class[] { String.class });
        Annotation[] methodAnnotations = method.getDeclaredAnnotations();

        for (Annotation a : methodAnnotations) {
            if (a instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) a;
                print("name: " + myAnnotation.name(), "value: ", myAnnotation.value());
            }
        }
        // 获取方法上指定的注解
        Annotation methodAnnotation = method.getAnnotation(MyAnnotation.class);

        // 获取方法参数上的注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class[] parameterTypes = method.getParameterTypes();

        int i = 0;
        for (Annotation[] paraAnnos : parameterAnnotations) {
            Class parameterType = parameterTypes[i++];

            for (Annotation paraAnno : paraAnnos) {
                if (paraAnno instanceof MyAnnotation) {
                    MyAnnotation myAnnotation = (MyAnnotation) paraAnno;
                    print("param: ", parameterType.getName(), "anno name: " + myAnnotation.name(), "anno value: ",
                            myAnnotation.value());
                }
            }
        }

        // 获取属性上的注解
        Field field = aClass.getField("field02");
        Annotation[] fieldAnnotations = field.getDeclaredAnnotations();

        for (Annotation a : fieldAnnotations) {
            if (a instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) a;
                print("name: " + myAnnotation.name(), "value: ", myAnnotation.value());
            }
        }
        // 获取属性上指定的注解
        Annotation fieldAnnotation = field.getAnnotation(MyAnnotation.class);

    }

    public void print(String hint, String... strings) {
        JR02Classes.print(hint, 15, strings);
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, NoSuchFieldException {
        JR08Annotations t = new JR08Annotations();
        t.test();
    }
}
