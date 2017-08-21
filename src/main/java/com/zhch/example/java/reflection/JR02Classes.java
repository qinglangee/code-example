package com.zhch.example.java.reflection;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.testng.annotations.Test;

/**
 * 通过反射可以获取类的这些信息
 *
 * Class Name<br>
 * Class Modifies (public, private, synchronized etc.)<br>
 * Package Info<br>
 * Superclass<br>
 * Implemented Interfaces<br>
 * Constructors<br>
 * Methods<br>
 * Fields<br>
 * Annotations<br>
 * 其它更多的可以查看Class文档 http://java.sun.com/javase/6/docs/api/java/lang/Class.html
 *
 * @author zhch 2017年8月16日
 *
 */
@Test
public class JR02Classes implements Serializable {

    public String name;
    private String priName;
    protected String proName;

    protected void test() throws ClassNotFoundException {
        // 得到类的java.lang.Class对象
        Class myObjectClass = JR02Classes.class;

        // 如果运行时才能知道类的名字
        String className = "com.zhch.example.java.reflection.JR02Classes";
        Class aClass = Class.forName(className);

        // 获取类的全名
        String name = aClass.getName();
        print("full name:", name);
        // 获取不包含包名的类名
        String simpleName = aClass.getSimpleName();
        print("simple name:", simpleName);

        // 获取类的修饰符，是一个数字
        int modifiers = aClass.getModifiers();
        // 判断修饰符类型
        Modifier.isAbstract(modifiers);
        Modifier.isFinal(modifiers);
        Modifier.isInterface(modifiers);
        Modifier.isNative(modifiers);
        Modifier.isPrivate(modifiers);
        Modifier.isProtected(modifiers);
        Modifier.isPublic(modifiers);
        Modifier.isStatic(modifiers);
        Modifier.isStrict(modifiers);
        Modifier.isSynchronized(modifiers);
        Modifier.isTransient(modifiers);
        Modifier.isVolatile(modifiers);

        // 获取包信息
        Package packageInfo = aClass.getPackage();
        print("包名:", packageInfo.getName());
        // 更多包信息 http://java.sun.com/javase/6/docs/api/java/lang/Package.html

        // 获取父类
        Class superclass = aClass.getSuperclass();
        print("父类:", superclass.getName());

        // 获取接口 , 只能取到本类明确实现的, 父类实现的需要递归才能取到
        Class[] interfaces = aClass.getInterfaces();
        for (Class c : interfaces) {
            print("接口:", c.getName());
        }

        // 获取构造函数
        Constructor[] constructors = aClass.getConstructors();
        for (Constructor c : constructors) {
            print("构造函数:", c.getName());
        }
        // 更多信息 http://tutorials.jenkov.com/java-reflection/constructors.html

        // 获取方法, 只能取到public的方法
        Method[] methods = aClass.getMethods();
        for(Method m : methods){
            print("方法:", m.getName());
        }

        // 获取属性, 也是只能取到public的
        Field[] fields = aClass.getFields();
        for(Field f : fields){
            print("属性:", f.getName());
        }

        // 注解
        Annotation[] annotations = aClass.getAnnotations();
        for(Annotation a : annotations){
            print("注解:", a.annotationType().getName());
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        JR02Classes t = new JR02Classes();
        t.test();
    }

    private void print(String hint, String... strings) {
        print(hint, 15, strings);
    }

    public static void print(String hint, int hintLength, String... strings) {
        System.out.print(hint);
        int length = hint.length();
        while (length < hintLength) {
            System.out.print(" ");
            length++;
        }
        for (String str : strings) {
            System.out.print(" - " + str);
        }
        System.out.println();
    }
}
