package com.zhch.util;

public class TempLog {
    public static void logHead(){
        String funcName = new Throwable().getStackTrace()[1].getMethodName();
        log("=====" + funcName + "===============================");
    }
    public static void log(Object msg){
        System.out.println(msg);
    }
}
