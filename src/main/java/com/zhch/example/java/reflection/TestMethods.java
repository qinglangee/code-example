package com.zhch.example.java.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Java 反射教程 http://tutorials.jenkov.com/java-reflection/methods.html
 * @author zhch Apr 14, 2017
 *
 */
public class TestMethods {

    private void private001() {
        System.out.println("这是一个私有方法");
    }

    protected void protected001(String arg) {
        System.out.println("这是一个保护方法 :" + arg);
    }

    public void public001(Long arg) {
        System.out.println("这是一个公共方法 001 arg:" + arg);
    }

    public void public002(long arg) {
        System.out.println("这是一个公共方法 002 arg:" + arg);
    }

    public void public003() {
    }
    
    public static void public004(){
        System.out.println("这是一个公有方法　004");
    }

    public void methods() {
        System.out.println("test methids() ==========================================");
        Class aClass = getClass(); // obtain class object
        Method[] methods = aClass.getMethods(); // 这会返回 class 所有的公有方法
        for (Method m : methods) {
            System.out.println("method:[" + m.getName() + "]   " + m);
        }
    }

    public void method() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        System.out.println("test methid() ==========================================");
        Class aClass = getClass(); // obtain class object
        try {
            // 这里会报错，因为保护和私有方法不能用 getMethod(...) 取到
            Method method = aClass.getMethod("protected001", new Class[] { String.class });
        } catch (Exception e) {
            System.out.println("xxx errMsg:" + e);
        }
        try {
            // 这里会报错，因为参数是原始类型时，用包装类型匹配不上
            Method method = aClass.getMethod("public002", new Class[] { Long.class });
        } catch (Exception e) {
            System.out.println("xxx errMsg:" + e);
        }
        // 根据方法名和参数取得方法对象
        Method method = aClass.getMethod("public001", new Class[] { Long.class });
        System.out.println("获取的方法：" + method);
        // 第一个参数是调用方法的对象，static方法可以传null, 后面的参数根据被调用的方法，需要几个给几个
        method.invoke(this, 1234567890L);

        Class[] parameterTypes = method.getParameterTypes(); // 方法参数列表
        Class returnType = method.getReturnType(); // 方法返回类型

        // 对于没有参数的方法，getMethod() 第二个参数传 null
        method = aClass.getMethod("public003", null);
        System.out.println("获取的方法：" + method);
        
        method = aClass.getMethod("public004", null);
        Object returnValue = method.invoke(null);
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        TestMethods t = new TestMethods();
        t.method();
        t.methods();
    }
}
