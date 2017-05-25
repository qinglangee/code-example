package com.zhch.example.java.generic;

import java.util.ArrayList;
import java.util.List;

public class ParamGeneric {

    // 在返回类型之前设置要用的范型类型
    public <T> List<T> parseObject(Class<T> clazz) throws InstantiationException, IllegalAccessException{
        T o = clazz.newInstance();
        List<T> list = new ArrayList<>();
        list.add(o);
        return list;
    }
    public <E,T> List<E> changeObject(Class<T> clazz) throws InstantiationException, IllegalAccessException{
        E o = null;
        List<E> list = new ArrayList<>();
        list.add(o);
        return list;
    }
    
    public void test() throws InstantiationException, IllegalAccessException {
        List<String> list = parseObject(String.class);
        list.forEach(a->System.out.println("str:" + a));
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ParamGeneric t = new ParamGeneric();
        t.test();
    }
}
