package com.zhch.example.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class JR03Constructors {
    private JR03Constructors() {
    }

    public JR03Constructors(String a) {
    }

    public JR03Constructors(String a, Integer b) {
    }

    public JR03Constructors(int a) {
    }

    public void test() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Class aClass = JR03Constructors.class;
        // 获取构造函数(只能取到public的)
        Constructor[] constructors = aClass.getConstructors();
        for (Constructor c : constructors) {
            print("构造函数:", c.getName());
            // 取构造函数参数
            Class[] parameterTypes = c.getParameterTypes();
            for (Class type : parameterTypes) {
                print("参数:", type.getName());
            }
        }

        Constructor constructor = aClass.getConstructor(new Class[] { String.class });
        print("指定参数的构造函数:", constructor.getName());

        // 用构造函数创建一个实例
        JR03Constructors myObject = (JR03Constructors) constructor.newInstance("constructor-arg1");
        print("实例:", myObject.getClass().getName());
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JR03Constructors t = new JR03Constructors();
        t.test();
    }

    public void print(String hint, String... strings) {
        JR02Classes.print(hint, 15, strings);
    }
}
