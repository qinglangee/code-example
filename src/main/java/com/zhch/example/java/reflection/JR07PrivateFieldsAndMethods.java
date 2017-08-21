package com.zhch.example.java.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用反射可以访问到类的私有属性<br>
 * 但在applet中使用这些方法时需要设置SecurityManager
 * @author zhch 2017年8月16日
 *
 */
public class JR07PrivateFieldsAndMethods {

    public void test() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        PrivateObj privateObject = new PrivateObj("The Private Value");
        // 获取私有属性, 只能取当前类声明的私有属性
        Field privateStringField = PrivateObj.class.getDeclaredField("name");
        privateStringField.setAccessible(true);
        String fieldValue = (String) privateStringField.get(privateObject);

        print("fieldValue = ", fieldValue);

        // 获取私有方法
        Method privateStringMethod = PrivateObj.class.getDeclaredMethod("getPrivateString", null);
        privateStringMethod.setAccessible(true);
        String returnValue = (String) privateStringMethod.invoke(privateObject, null);

        print("returnValue = ", returnValue);

    }

    public void print(String hint, String... strings) {
        JR02Classes.print(hint, 15, strings);
    }

    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        JR07PrivateFieldsAndMethods t = new JR07PrivateFieldsAndMethods();
        t.test();
    }
}

class PrivateObj {
    private String name;

    public PrivateObj() {
    }

    public PrivateObj(String name) {
        this.name = name;
    }

    private String getPrivateString() {
        return this.name;
    }
}
