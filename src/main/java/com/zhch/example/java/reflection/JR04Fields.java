package com.zhch.example.java.reflection;

import java.lang.reflect.Field;

public class JR04Fields {

    public static String staticName = "stName";
    public String name;
    public String color;
    private int age;
    protected int height;

    public void test() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Class aClass = JR04Fields.class;
        Field[] fields = aClass.getFields();
        for(Field f: fields){
            print("属性:", f.getName(), f.getType().getName());
        }
        // 获取指定的属性
        Field field = aClass.getField("name");
        print("指定属性:", field.getName());

        // 属性 get set
        JR04Fields objectInstance = new JR04Fields();
        print("name value:", objectInstance.name);
        Object value = field.get(objectInstance);
        print("name value:", String.valueOf(value));
        field.set(objectInstance, "abcdefg");
        print("name value:", objectInstance.name);
        print("name value:", String.valueOf(value));
        value = field.get(objectInstance);
        print("name value:", String.valueOf(value));

        // 静态属性set get时obj值用null
        Field staticField = aClass.getField("staticName");
        Object stValue = staticField.get(null);
        print("static name:", String.valueOf(stValue));
        stValue = staticField.get(objectInstance);
    }

    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        JR04Fields t = new JR04Fields();
        t.test();
    }

    public void print(String hint, String... strings) {
        JR02Classes.print(hint, 15, strings);
    }
}
