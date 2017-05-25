package com.zhch.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射操作工具类
 * @author zhch Apr 20, 2017
 *
 */
public class ClassUtil {
    private static Logger LOG = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 根据类和字段返回　getter 方法
     * @param classT
     * @param field
     * @return
     */
    public static <T> Method findGetter(Class<T> classT, Field field) {
        Method method = null;
        try {
            method = classT.getDeclaredMethod(fieldToGetter(field.getName()));
        } catch (NoSuchMethodException e) {
            // boolean 型也可能是isXXX 的方法
            if(field.getType() == Boolean.TYPE || field.getType().equals(Boolean.class)){
                try {
                    method = classT.getDeclaredMethod(boolToGetter(field.getName()));
                } catch (NoSuchMethodException | SecurityException e1) {
                    LOG.error("获取方法出错", e);
                }
            }else{
                LOG.error("获取方法出错", e);
            }
        } catch (SecurityException e) {
            LOG.error("获取方法出错", e);
        }
        return method;
    }
    
    /**
     * 根据类和字段返回　setter 方法
     * @param classT
     * @param field
     * @return
     */
    public static <T> Method findSetter(Class<T> classT, Field field) {
        try {
            Method method = classT.getDeclaredMethod(fieldToSetter(field.getName()), field.getType());
            return method;
        } catch (NoSuchMethodException | SecurityException e) {
            LOG.error("获取方法出错", e);
        }
        return null;
    }
    
    /**
     * 把字段名字转换成　setter 方法名
     * @param name
     * @return
     */
    public static String fieldToGetter(String name){
        if(name == null|| name.length() < 1){
            return null;
        }
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
    /**
     * 把字段名字转换成　setter 方法名
     * @param name
     * @return
     */
    public static String boolToGetter(String name){
        if(name == null|| name.length() < 1){
            return null;
        }
        return "is" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    

    /**
     * 把字段名字转换成　setter 方法名
     * @param name
     * @return
     */
    public static String fieldToSetter(String name){
        if(name == null|| name.length() < 1){
            return null;
        }
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

}
