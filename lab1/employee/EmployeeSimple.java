package employee;

import employee.department.Department;

// Task 2.2: Дослідити можливість розробки класу без конструктора класу
public class EmployeeSimple {
    private int id;
    private String name;
    private Department department;
    private double salary;

    public String getEmployeeData() {
        return "ID: " + id + ", Name: " + name + ", Department: " + department + ", Salary: $" + salary;
    }

    public static void main(String[] args) {
        EmployeeSimple employee = new EmployeeSimple();
        System.out.println(employee.getEmployeeData());
    }
}
