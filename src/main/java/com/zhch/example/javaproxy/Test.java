package com.zhch.example.javaproxy;

import java.lang.reflect.Proxy;

public class Test {
	
	public static void main(String[] args) {
		Employee e1 = new Employee("张三", 4000);
		Employee e2 = new Employee("李四", 5000);
		PaySalary pay = new PaySalaryImpl();
		//业务对象  
		PaySalaryImpl obj = new PaySalaryImpl();   
           
        //拦截器对象  
		ZhchPaySalaryProxy handler = new ZhchPaySalaryProxy(obj);   
           
        //返回业务对象的代理对象  
		PaySalary proxy = (PaySalary)Proxy.newProxyInstance(   
                obj.getClass().getClassLoader(),    
                obj.getClass().getInterfaces(),    
                handler);   
           
        //通过代理对象执行业务对象的方法  
        proxy.paySalary(e1);
        proxy.paySalary(e2);
	}
}
