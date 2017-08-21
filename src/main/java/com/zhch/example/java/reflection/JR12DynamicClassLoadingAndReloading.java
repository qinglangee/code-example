package com.zhch.example.java.reflection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 关于动态加载和重新加载
 * http://tutorials.jenkov.com/java-reflection/dynamic-class-loading-reloading.html<br>
 * 自定义 ClassLoader 加载类的顺序<br>
 * 1. 检查类是否已经加载了<br>
 * 2. 如果没有,就让父加载器去加载这个类<br>
 * 3. 如果父加载器不能加载这个类,就尝试自己加载这个类<br>
 * @author zhch 2017年8月18日
 *
 */
public class JR12DynamicClassLoadingAndReloading {
    /**
     * 动态加载类,很简单的哦,就调用 classLoader 的loadClass方法就可以了...
     */
    public void dynamicLoading() {
        ClassLoader classLoader = JR12DynamicClassLoadingAndReloading.class.getClassLoader();
        try {
            Class aClass = classLoader.loadClass("com.jenkov.MyClass");
            System.out.println("aClass.getName() = " + aClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态重新加载要麻烦一些, <br>
     * 1. Java 内建的 ClassLoader 加载类之前都会检查它有没有被加载过, 不会加载第二次, 所以 Java 内建的
     * ClassLoader 是不能实现重新加载的<br>
     * 2. 每个加载的类需要用 ClassLoader.resolve() 方法进行链接,
     * 但resolve()方法不允许同一个ClassLoader链接同一个类两次, 所以每次重新加载类都需要新的ClassLoader实例.<br>
     * 3. 不同的加载器加载的类, 即使类名完全相同, 也被认为是不同的类, 不能进行强制转换. 所以只能重新加载接口的实现类, 或被引用类的子类,
     * 而引用的是接口或父类<br>
     */
    public void dynamicReloading() {

    }

    public static void main(String[] args){

    }
    public class MyObject extends MyObjectSuperClass implements AnInterface2{
        //... body of class ... override superclass methods
        //    or implement interface methods
    }

    /**
     * 使用自定义加载器的例子
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void useCustomClassLoader()
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
        MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
        Class myObjectClass = classLoader.loadClass("reflection.MyObject");

        AnInterface2 object1 = (AnInterface2) myObjectClass.newInstance();

        MyObjectSuperClass object2 = (MyObjectSuperClass) myObjectClass.newInstance();

        // create new class loader so classes can be reloaded.
        classLoader = new MyClassLoader(parentClassLoader);
        myObjectClass = classLoader.loadClass("reflection.MyObject");

        object1 = (AnInterface2) myObjectClass.newInstance();
        object2 = (MyObjectSuperClass) myObjectClass.newInstance();

    }
}

interface AnInterface2 {

}

class MyObjectSuperClass {

}

/**
 * 一个自定义加载器的例子
 * @author zhch 2017年8月18日
 *
 */
class MyClassLoader extends ClassLoader {

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        if (!"reflection.MyObject".equals(name))
            return super.loadClass(name);

        try {
            String url = "file:C:/data/projects/tutorials/web/WEB-INF/" + "classes/reflection/MyObject.class";
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while (data != -1) {
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass("reflection.MyObject", classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
