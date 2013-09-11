package com.zhch.example.javaproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class ZhchPaySalaryProxy implements InvocationHandler {   
    //目标对象  
    private Object targetObject;   
       
    public ZhchPaySalaryProxy(Object targetObject){   
        this.targetObject = targetObject;   
    }   
       
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {   
        Employee e = (Employee)args[0];
        if(e.getSalary()> 4500){
        	e.setIncome(e.getSalary() * 0.7);
        }
  
        Object result = method.invoke(this.targetObject, args);   
  
           
        return result;   
    }   
}  
