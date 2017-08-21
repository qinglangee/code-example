package com.zhch.example.java.reflection;

import java.lang.reflect.Method;

/**
 * tutorial from http://tutorials.jenkov.com/java-reflection/index.html
 * @author zhch 2017年8月16日
 *
 */
public class JR01Tutorial {

    public static void main(String[] args) {
        Method[] methods = JR01Tutorial.class.getMethods();

        for(Method method : methods){
            System.out.println("method = " + method.getName());
        }
    }
}
