package com.zhch.example.java.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 运行时范型化的类中没有具体类型信息,但是用到范型类的属性可以取到范型的具体信息,比较难理解,看实例
 * @author zhch 2017年8月16日
 *
 */
public class JR09Generics {
    public void test() throws NoSuchMethodException, SecurityException, NoSuchFieldException {

        // 取返回值中List的范型类型
        String[] names = new String[]{"getStringList", "getStringMap"};
        for(String name: names){
            System.out.println("name:" + name);
            Method method = MyClass.class.getMethod(name, null);

            Type returnType = method.getGenericReturnType();

            if(returnType instanceof ParameterizedType){
                ParameterizedType type = (ParameterizedType) returnType;
                Type[] typeArguments = type.getActualTypeArguments();
                for(Type typeArgument : typeArguments){
                    Class typeArgClass = (Class) typeArgument;
                    System.out.println("typeArgClass = " + typeArgClass);
                }
            }
        }


        // 取参数中的范型信息
        Method method = MyClass.class.getMethod("setStringList", List.class);

        Type[] genericParameterTypes = method.getGenericParameterTypes();

        for(Type genericParameterType : genericParameterTypes){
            if(genericParameterType instanceof ParameterizedType){
                ParameterizedType aType = (ParameterizedType) genericParameterType;
                Type[] parameterArgTypes = aType.getActualTypeArguments();
                for(Type parameterArgType : parameterArgTypes){
                    Class parameterArgClass = (Class) parameterArgType;
                    System.out.println("parameterArgClass = " + parameterArgClass);
                }
            }
        }

        // 取属性中的范型信息
        Field field = MyClass.class.getField("stringList");

        Type genericFieldType = field.getGenericType();

        if(genericFieldType instanceof ParameterizedType){
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for(Type fieldArgType : fieldArgTypes){
                Class fieldArgClass = (Class) fieldArgType;
                System.out.println("fieldArgClass = " + fieldArgClass);
            }
        }
    }



    public static void main(String[] args) throws NoSuchMethodException, SecurityException {
        JR09Generics t = new JR09Generics();
        try {
            t.test();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class MyClass {

    public List<String> stringList = new ArrayList<>();

    // 这个方法的返回类型通过反射可以得到返回的是一个String的List, 而不仅仅是一个Object的List
    public List<String> getStringList(){
      return this.stringList;
    }
    public Map<String, Integer> getStringMap(){
        return null;
    }

    public void setStringList(List<String> list){
        this.stringList = list;
      }
  }
