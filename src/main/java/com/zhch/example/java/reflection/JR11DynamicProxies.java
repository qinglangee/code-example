package com.zhch.example.java.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * http://tutorials.jenkov.com/java-reflection/dynamic-proxies.html
 * @author zhch 2017年8月18日
 *
 */
public class JR11DynamicProxies {
    public void test() {
        InvocationHandler handler = new MyInvocationHandler();
        MyInterface proxy = (MyInterface) Proxy.newProxyInstance(MyInterface.class.getClassLoader(),
                new Class[] { MyInterface.class }, handler);
    }

    public static void main(String[] args) {
        JR11DynamicProxies t = new JR11DynamicProxies();
        t.test();
    }
}

interface MyInterface {

}

class MyInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        return null;
    }

}