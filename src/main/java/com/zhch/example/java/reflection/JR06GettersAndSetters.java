package com.zhch.example.java.reflection;

import java.lang.reflect.Method;

public class JR06GettersAndSetters {
    public void test() {
        printGettersSetters(JR06GettersAndSetters.class);
    }

    public void print(String hint, String... strings) {
        JR02Classes.print(hint, 15, strings);
    }

    /**
     * 列出一个类的所有getter setter方法
     * @param aClass
     */
    public static void printGettersSetters(Class aClass) {
        Method[] methods = aClass.getMethods();

        for (Method method : methods) {
            if (isGetter(method))
                System.out.println("getter: " + method);
            if (isSetter(method))
                System.out.println("setter: " + method);
        }
    }

    /**
     * 判断方法是不是getter<br>
     * 判断标准:getter 是以get开头,没有参数并且有返回值的方法
     * @param method
     * @return
     */
    public static boolean isGetter(Method method){
        if(!method.getName().startsWith("get"))      return false;
        if(method.getParameterTypes().length != 0)   return false;
        if(void.class.equals(method.getReturnType())) return false;
        return true;
      }

    /**
     * 判断方法是不是setter<br>
     * 判断标准:setter 是以set开头,有一个参数的方法, 返回值可能是各种形式,无需判断
     * @param method
     * @return
     */
    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set"))
            return false;
        if (method.getParameterTypes().length != 1)
            return false;
        return true;
    }

    public static void main(String[] args) {
        JR06GettersAndSetters t = new JR06GettersAndSetters();
        t.test();
    }
}
