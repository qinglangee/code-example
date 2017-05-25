package com.zhch.example.java.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class TestFieldType {
    /**
     * 根据类和字段返回　getter 方法
     * @param classT
     * @param field
     * @return
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public static <T> Method findGetter(Class<T> classT, Field field) throws NoSuchMethodException, SecurityException {
        Method method = null;
        method = classT.getDeclaredMethod("getGood");
        // 判断基本类型与包装类型
        if(field.getType() == Boolean.TYPE || field.getType().equals(Boolean.class)){
            method = classT.getDeclaredMethod("isGood");
        }
        return method;
    }
}
