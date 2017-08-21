package com.zhch.example.java.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JR05Methods {

    public void test() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class aClass = JR05Methods.class;
        // 取所有public 方法
        Method[] methods = aClass.getMethods();
        System.out.println("001============================================");
        for(Method m : methods){
            print("方法名:", m.getName());
            // 方法参数
            Class[] parameterTypes = m.getParameterTypes();
            for(Class c : parameterTypes){
                print("方法参数:", c.getName());
            }

            // 返回类型
            Class returnType = m.getReturnType();
            print("返回值类型:", returnType.getName());
        }
        System.out.println("002============================================");

        // 根据名字和参数获取方法
        Method method = aClass.getMethod("doSomething", new Class[] { String.class });
        // 没有参数的方法
        Method method2 = aClass.getMethod("doSomething", null);

        Object returnValue = method.invoke(this, "parameter-value1");
    }

    public void doSomething() {

    }
    public String doSomething(String arg) {
        System.out.println("do something :" + arg);
        return "";
    }

    public void print(String hint, String... strings) {
        JR02Classes.print(hint, 15, strings);
    }
    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JR05Methods t = new JR05Methods();
        t.test();
    }
}
