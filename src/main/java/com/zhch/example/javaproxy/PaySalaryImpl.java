package com.zhch.example.javaproxy;


public class PaySalaryImpl implements PaySalary{

	@Override
	public void paySalary(Employee employee) {
		System.out.println(employee.getName() + " 实发工资" + employee.getIncome());
	}
}
