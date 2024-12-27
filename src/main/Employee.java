package main;

// Abstract base class for all employee types with common attributes
abstract class Employee {
	private String employeeCode; // Unique identifier for each employee
	private String name; // Employee's full name
	private String sex; // Sex of the employee
	private double salary; // Employee's current salary

	// Constructor to initialise employee details
	protected Employee(String employeeCode, String name, String sex, double salary) {
		this.employeeCode = employeeCode;
		this.name = name;
		this.sex = sex;
		this.salary = salary;
	}

	// Getter and setter methods for each attribute
	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}

// Concrete subclasses representing different employee positions
class Manager extends Employee {
	public Manager(String employeeCode, String name, String sex, double salary) {
		super(employeeCode, name, sex, salary);
	}
}

class Supervisor extends Employee {
	public Supervisor(String employeeCode, String name, String sex, double salary) {
		super(employeeCode, name, sex, salary);
	}
}

class Admin extends Employee {
	public Admin(String employeeCode, String name, String sex, double salary) {
		super(employeeCode, name, sex, salary);
	}
}