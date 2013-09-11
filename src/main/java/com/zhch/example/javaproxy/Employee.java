package com.zhch.example.javaproxy;

public class Employee {
	/** 姓名 */
	private String name;
	/** 工资 */
	private double salary;
	/** 实际发的工资 */
	private double income;
	
	public Employee(String name, double salary){
		this.name = name;
		this.salary = salary;
		this.income = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}
}
