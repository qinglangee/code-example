package com.zhch.example.java.reflection;

import java.lang.reflect.Array;

public class JR10Arrays {
    public void test() throws ClassNotFoundException {
        // 通过反射创建数组
        int[] intArray = (int[]) Array.newInstance(int.class, 3);

        // 存取数组数据
        Array.set(intArray, 0, 123);
        Array.set(intArray, 1, 456);
        Array.set(intArray, 2, 789);

        System.out.println("intArray[0] = " + Array.get(intArray, 0));
        System.out.println("intArray[1] = " + Array.get(intArray, 1));
        System.out.println("intArray[2] = " + Array.get(intArray, 2));

        // 获取数组的Class对象
        Class stringArrayClass = String[].class;
        print("stringArrayClass:", stringArrayClass.getName());

        // int 数组
        Class intArrayClass = Class.forName("[I");
        print("intArrayClass:", intArrayClass.getName());
        // 基本类型的数组
        Class arr01 = int[].class; // [I
        print("arr01:", arr01.getName());
        arr01 = short[].class; // [S
        print("arr01:", arr01.getName());
        arr01 = long[].class; // [J
        print("arr01:", arr01.getName());
        arr01 = float[].class; // [F
        print("arr01:", arr01.getName());
        arr01 = double[].class; // [D
        print("arr01:", arr01.getName());
        arr01 = byte[].class; // [B
        print("arr01:", arr01.getName());
        arr01 = char[].class; // [C
        print("arr01:", arr01.getName());
        arr01 = boolean[].class; // [Z
        print("arr01:", arr01.getName());

        // Object 类型数组
        // 注意:左边有 "[L" 右边有 ";"
        Class stringArrayClass2 = Class.forName("[Ljava.lang.String;");
        print("stringArrayClass2:", stringArrayClass2.getName());

        // 通过类型获取对应的数组类型
        Class theClass = getClass("int");
        Class arrayClass = Array.newInstance(theClass, 0).getClass();
        print("arrayClass:", arrayClass.getName());

        // 判断是不是数组类型
        Class stringArrayClass3 = Array.newInstance(String.class, 0).getClass();
        System.out.println("is array: " + stringArrayClass3.isArray());

        // 通过数组类型获取元素类型
        String[] strings = new String[3];
        Class stringArrayClass4 = strings.getClass();
        Class stringArrayComponentType = stringArrayClass4.getComponentType();
        System.out.println(stringArrayComponentType);
    }

    /**
     * 获取基本类型和所有类型的Class
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public Class getClass(String className) throws ClassNotFoundException{
        if("int" .equals(className)) return int .class;
        if("long".equals(className)) return long.class;
        // ...
        return Class.forName(className);
      }

    public void print(String hint, String... strings) {
        JR02Classes.print(hint, 15, strings);
    }
    public static void main(String[] args) throws ClassNotFoundException {
        JR10Arrays t = new JR10Arrays();
        t.test();
    }
}
