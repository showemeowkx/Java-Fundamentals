import java.util.Random;

import employee.Employee;
import employee.department.Department;

public class Main {
    private static Department[] departments = Department.values();
    static Random random = new Random();   

    private static int getRandomDepartmentIndex() {
        return random.nextInt(departments.length);
    }

    private static void printEmployees(Employee[] employees) {
        for (int i=0; i<employees.length; i++) {
            System.out.println(i + 1 + ". " + employees[i].getEmployeeData());
        }
    }

    private static double getAverageSalary(Employee[] employees) {
        double totalSalary = 0;
        for (Employee employee : employees) {
            totalSalary += employee.getSalary();
        }

        // Task 4.2: Створити статичний метод та надати приклад його використання в головній програмі
        return totalSalary / Employee.getTotalEmployees();
    }

    private static Employee getHighestSalaryEmployee(Employee[] employees) {
        Employee topEmployee = employees[0];
        
        for (Employee employee : employees) {
            if (employee.getSalary() > topEmployee.getSalary()) {
                topEmployee = employee;
            }
        }
        return topEmployee;
    }
    public static void main(String[] args) {
        // Task 1.2: У головній програмі створити масив об’єктів класу
        Employee[] employees = new Employee[10];

        System.out.println("--- BEFORE RAISE ---");
        for (int i=0; i<10; i++) {
            Employee employee = new Employee();

            employee.setName("Employee " + (i + 1));
            employee.setDepartment(departments[getRandomDepartmentIndex()]);
            employee.setSalary(Math.floor(Math.random() * 10000));

            employees[i] = employee;
        }

        printEmployees(employees);

        // Task 1.3: Виконати тестові розрахункові дії з об’єктами з використанням методів класу
        System.out.println("Total Employees: " + Employee.getTotalEmployees());
        System.out.println("Average Salary: $" + getAverageSalary(employees));
        System.out.println("Employee with the highest salary: " + getHighestSalaryEmployee(employees).getName() + ";  $" + getHighestSalaryEmployee(employees).getSalary());

        System.out.println("--- AFTER RAISE ---");
        for (int i=0; i<Employee.getTotalEmployees()-1; i++) {
            int probRaise = random.nextInt(2);

            if (probRaise == 1) {
                System.out.println(employees[i].getName() + " got a raise!");
                int probPercent = random.nextInt(2);

                if (probPercent == 1) {
                    employees[i].giveRaise(Math.floor(Math.random() * 100), true);
                } else {
                    employees[i].giveRaise(Math.floor(Math.random() * 1000));
                }
                ;
            }
        }
        
        printEmployees(employees);

        System.out.println("Average Salary: $" + getAverageSalary(employees));
        System.out.println("Employee with the highest salary: " + getHighestSalaryEmployee(employees).getName() + ";  $" + getHighestSalaryEmployee(employees).getSalary());
    }
}
