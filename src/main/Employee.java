package main;

// Abstract base class for all employee types with common attributes
abstract class Employee {
	private String employeeCode; // Unique identifier for each employee
	private String name; // Employee's full name
	private String sex; // Sex of the employee
	private double salary; // Employee's current salary

	// Constructor to initialise employee details
	protected Employee(String employeeCode, String name, String sex) {
		this.employeeCode = employeeCode;
		this.name = name;
		this.sex = sex;
		this.salary = this.getBaseSalary(); // Set initial salary based on position
	}

	// Abstract method to be implemented by each employee type
	protected abstract double getBaseSalary();

	// Get bonus percentage for the position
	protected abstract double getBonusPercentage();

	// Apply bonus to salary
	public void applyBonus() {
		this.salary = this.salary * (1 + getBonusPercentage());
	}

	// Reset salary to base
	public void resetToBaseSalary() {
		this.salary = this.getBaseSalary();
	}

	// Getter and setter methods
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

// Concrete subclasses with position-specific salary logic
class Manager extends Employee {
	private static final double BASE_SALARY = 8000000;
	private static final double BONUS_PERCENTAGE = 0.10;

	public Manager(String employeeCode, String name, String sex) {
		super(employeeCode, name, sex);
	}

	@Override
	protected double getBaseSalary() {
		return BASE_SALARY;
	}

	@Override
	protected double getBonusPercentage() {
		return BONUS_PERCENTAGE;
	}
}

class Supervisor extends Employee {
	private static final double BASE_SALARY = 6000000;
	private static final double BONUS_PERCENTAGE = 0.075;

	public Supervisor(String employeeCode, String name, String sex) {
		super(employeeCode, name, sex);
	}

	@Override
	protected double getBaseSalary() {
		return BASE_SALARY;
	}

	@Override
	protected double getBonusPercentage() {
		return BONUS_PERCENTAGE;
	}
}

class Admin extends Employee {
	private static final double BASE_SALARY = 4000000;
	private static final double BONUS_PERCENTAGE = 0.05;

	public Admin(String employeeCode, String name, String sex) {
		super(employeeCode, name, sex);
	}

	@Override
	protected double getBaseSalary() {
		return BASE_SALARY;
	}

	@Override
	protected double getBonusPercentage() {
		return BONUS_PERCENTAGE;
	}
}