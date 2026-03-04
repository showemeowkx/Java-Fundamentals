package employee;
import employee.department.Department;

// Task 1.1: Створити клас згідно з варіантом завдання
public class Employee {
    // Task 3: Дослідити поняття статичного поля класу. Створити статичне поле для класу
    private static int totalEmployees = 0;
    private static int nextId = 1;

    private int id;
    private String name;
    private Department department;
    private double salary;

    // Task 2.1: Дослідити можливість використання кількох конструкторів класу
    public Employee() {
        id = nextId++;
        this.name = "Unknown";
        this.department = null;
        this.salary = 0;
        totalEmployees++;
    }

    public Employee(String name, Department department, double salary) {
        id = nextId++;
        this.name = name;
        this.department = department;
        this.salary = salary;
        totalEmployees++;
    }

    public int getId() {
        return id;
    }

    public String getEmployeeData() {
        return "ID: " + id + ", Name: " + name + ", Department: " + department + ", Salary: $" + salary;
    }

    // Task 4.1: Дослідити поняття статичного методу класу
    public static int getTotalEmployees() {
        return totalEmployees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        } else if (name.length() > 100 || name.length() < 2) {
            throw new IllegalArgumentException("Name must be between 2 and 100 characters.");
        }

        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department cannot be null.");
        }
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.salary = salary;
    }

    // Task 2.3: Дослідити можливість визначення методу з  одним іменем, але різним списком аргументів
    public void giveRaise(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Raise amount cannot be negative.");
        }
        this.salary += amount;
    }

    public void giveRaise(double percentage, boolean isPercentage) {
        if (isPercentage) {
            if (percentage < 0) {
                throw new IllegalArgumentException("Raise percentage cannot be negative.");
            }

            double amount = this.salary * (percentage / 100);
            this.salary += amount;
        }
    }
}

