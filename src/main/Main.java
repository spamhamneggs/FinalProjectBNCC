package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Main {
	private static ArrayList<Employee> employees = new ArrayList<>(); // Stores all employee records
	private static Scanner scan = new Scanner(System.in); // User input scanner
	private static Random random = new Random(); // Random generator for employee code

	// Display main menu and handle user interactions
	private static void printMenu() {
		int choose;
		do {
			// Menu options for employee management
			System.out.println("\nSistem Management Karyawan");
			System.out.println("1. Insert Data Karyawan");
			System.out.println("2. View Data Karyawan");
			System.out.println("3. Update Data Karyawan");
			System.out.println("4. Delete Data Karyawan");
			System.out.println("5. Exit");
			System.out.print("Pilih menu (1-5): ");
			choose = scan.nextInt();

			// Route to appropriate method based on user choice
			switch (choose) {
				case 1:
					insertEmployee();
					break;
				case 2:
					viewEmployees();
					break;
				case 3:
					updateEmployee();
					break;
				case 4:
					deleteEmployee();
					break;
				case 5:
					System.out.println("\nTerima kasih telah mengunakan program ini!");
					break;
				default:
					System.out.println("Pilihan invalid!");
			}
		} while (choose != 5);
	}

	// Generate unique random employee code (2 letters + 4 digits)
	private static String generateEmployeeCode() {
		String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder code = new StringBuilder();
		code.append(alphabets.charAt(random.nextInt(26)));
		code.append(alphabets.charAt(random.nextInt(26)));
		code.append("-");
		code.append(String.format("%04d", random.nextInt(10000)));
		return code.toString();
	}

	// Apply salary bonuses based on number of employees in a position
	private static void applyBonuses(Class<?> positionClass) {
		int count = 0;
		// Count employees in specific position
		for (Employee emp : employees) {
			if (positionClass.isInstance(emp)) {
				count++;
			}
		}

		// Determine number of bonus recipients
		int bonusRecipients = 0;
		if (count >= 4 && count < 7) {
			bonusRecipients = 3;
		} else if (count >= 7) {
			bonusRecipients = 6;
		}

		// Apply bonus if conditions met
		if (bonusRecipients > 0) {
			// Prepare list of eligible employees
			ArrayList<Employee> eligibleEmployees = new ArrayList<>();
			for (Employee emp : employees) {
				if (positionClass.isInstance(emp)) {
					emp.resetToBaseSalary();
					eligibleEmployees.add(emp);
				}
			}

			// Apply bonus to top recipients
			ArrayList<String> bonusReceiverIds = new ArrayList<>();
			int bonusCount = 0;
			for (Employee emp : eligibleEmployees) {
				if (bonusCount < bonusRecipients) {
					emp.applyBonus();
					bonusReceiverIds.add(emp.getEmployeeCode());
					bonusCount++;
				}
			}

			// Print bonus recipients
			if (!bonusReceiverIds.isEmpty()) {
				Employee sampleEmployee = eligibleEmployees.get(0);
				System.out.printf("Bonus sebesar %.1f%% telah diberikan kepada karyawan dengan id ",
						sampleEmployee.getBonusPercentage() * 100);
				System.out.print(String.join(", ", bonusReceiverIds));
				System.out.println("");
			}
		}
	}

	// Method to insert new employee with input validation
	private static void insertEmployee() {
		System.out.println("\nInsert Data Karyawan");
		scan.nextLine(); // Clear Buffer

		// Validate name (min 3 characters)
		String name;
		do {
			System.out.print("Input nama karyawan [min 3 huruf]: ");
			name = scan.nextLine();
		} while (name.length() < 3);

		// Validate sex (Laki-Laki or Perempuan)
		String sex;
		do {
			System.out.print("Input jenis kelamin [Laki-Laki | Perempuan] (Case Sensitive): ");
			sex = scan.nextLine();
		} while (!sex.equals("Laki-Laki") && !sex.equals("Perempuan"));

		// Validate position (Manager, Supervisor, Admin)
		String position;
		do {
			System.out.print("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
			position = scan.nextLine();
		} while (!position.equals("Manager") && !position.equals("Supervisor") && !position.equals("Admin"));

		// Create new employee based on position
		String employeeCode = generateEmployeeCode();
		Employee newEmployee = null;

		switch (position) {
			case "Manager":
				newEmployee = new Manager(employeeCode, name, sex);
				break;
			case "Supervisor":
				newEmployee = new Supervisor(employeeCode, name, sex);
				break;
			case "Admin":
				newEmployee = new Admin(employeeCode, name, sex);
				break;
		}

		employees.add(newEmployee);

		// Confirm insertion and apply potential bonuses
		System.out.println("Berhasil menambah karyawan dengan ID " + employeeCode);
		applyBonuses(newEmployee.getClass());

		System.out.println("Press ENTER to return");
		scan.nextLine();
	}

	// Method to view all employees sorted by name
	private static void viewEmployees() {
		if (employees.isEmpty()) {
			System.out.println("\nTidak ditemukan karyawan!");
			return;
		}

		// Sort employees by name
		Collections.sort(employees, Comparator.comparing(Employee::getName));

		// Display employee details in tabular format
		System.out.println("\nDaftar Karyawan:");
		System.out.println("|----|---------------|---------------|---------------|------------|---------------|");
		System.out.println("| No | Kode Karyawan | Nama Keryawan | Jenis Kelamin | Jabatan    | Gaji Karyawan |");
		System.out.println("|----|---------------|---------------|---------------|------------|---------------|");

		int i = 1;
		for (Employee emp : employees) {
			// Determine position based on instance type
			String position = emp instanceof Manager ? "Manager" : emp instanceof Supervisor ? "Supervisor" : "Admin";

			// Print employee details
			System.out.printf("| %-2d | %-13s | %-13s | %-13s | %-10s | %-13.0f |\n", i++, emp.getEmployeeCode(),
					emp.getName(), emp.getSex(), position, emp.getSalary());
		}

		System.out.println("");
	}

	// Method to update existing employee details
	private static void updateEmployee() {
		viewEmployees();
		if (employees.isEmpty())
			return;

		// Select employee to update
		System.out.print("\nInput No. karyawan yang akan diupdate: ");
		int index = scan.nextInt();
		scan.nextLine(); // Clear buffer

		// Validate selected index
		if (index < 1 || index > employees.size()) {
			System.out.println("No. Karyawan Invalid!");
			return;
		}

		// Prepare for potential position change
		Employee currentEmployee = employees.get(index - 1);
		Class<?> oldPositionClass = currentEmployee.getClass();

		// Validate new name
		String newName;
		do {
			System.out.print("Input nama karyawan baru [min 3 huruf]: ");
			newName = scan.nextLine();
		} while (newName.length() < 3);

		// Validate new sex
		String newSex;
		do {
			System.out.print("Input jenis kelamin baru [Laki-Laki | Perempuan] (Case Sensitive): ");
			newSex = scan.nextLine();
		} while (!newSex.equals("Laki-Laki") && !newSex.equals("Perempuan"));

		// Validate new position
		String newPosition;
		do {
			System.out.print("Input jabatan baru [Manager | Supervisor | Admin] (Case Sensitive): ");
			newPosition = scan.nextLine();
		} while (!newPosition.equals("Manager") && !newPosition.equals("Supervisor") && !newPosition.equals("Admin"));

		// Create updated employee
		Employee updatedEmployee = null;
		switch (newPosition) {
			case "Manager":
				updatedEmployee = new Manager(currentEmployee.getEmployeeCode(), newName, newSex);
				break;
			case "Supervisor":
				updatedEmployee = new Supervisor(currentEmployee.getEmployeeCode(), newName, newSex);
				break;
			case "Admin":
				updatedEmployee = new Admin(currentEmployee.getEmployeeCode(), newName, newSex);
				break;
		}

		// Replace employee and apply bonuses if position changed
		employees.set(index - 1, updatedEmployee);
		System.out.println("Berhasil mengupdate karyawan!");
		if (!oldPositionClass.equals(updatedEmployee.getClass())) {
			applyBonuses(oldPositionClass);
			applyBonuses(updatedEmployee.getClass());
		}

		System.out.println("Press ENTER to return");
		scan.nextLine();
	}

	// Method to delete an employee
	private static void deleteEmployee() {
		viewEmployees();
		if (employees.isEmpty())
			return;

		// Select employee to delete
		System.out.print("\nInput No. karyawan yang akan didelete: ");
		int index = scan.nextInt();
		scan.nextLine(); // Clear buffer

		// Validate selected index
		if (index < 1 || index > employees.size()) {
			System.out.println("No. Karyawan Invalid!");
			return;
		}

		// Remove employee and apply potential bonuses
		Employee emp = employees.remove(index - 1);
		System.out.println("Berhasil mendelete karyawan!");
		applyBonuses(emp.getClass());

		System.out.println("Press ENTER to return");
		scan.nextLine();
	}

	// Main method to start the employee management application
	public static void main(String[] args) {
		printMenu();
		scan.close();
	}
}