import java.util.Random;

import auditory.Auditory;
import employee.Employee;
import employee.Laborant;

public class Main {
    static Random random = new Random();   

    private static void printEmployees(Employee[] employees) {
        for (int i=0; i<employees.length; i++) {
            System.out.println(i + 1 + ". " + employees[i].getEmployeeData());
        }
    }

    private static void getAuditoriesForEmployee(Employee employee) {
        Auditory[] auditories = employee.getAuditories();
        System.out.println(employee.getName() + " works in:");

        if (auditories == null || auditories.length == 0) {
            System.out.println("\tNo auditories assigned");
            return;
        }

        for (Auditory a: auditories) {
            System.out.println("\t" + "Auditory " + a.getId() + " (" + a.getForSubject() + ")");
        }
    } 

    public static void main(String[] args) {
        Auditory a1 = new Auditory(1, "Informatics", 70);
        Auditory a2 = new Auditory(2, "Mathematics", 30);
        Auditory a3 = new Auditory(1, "Physics", 50);

        Auditory.Equipment projector = a1.new Equipment("Projector", true);
        System.out.println("--- EQUIPMENT TEST ---");
        System.out.println(projector.getStatus());

        Employee[] employees = new Employee[5];

        employees[0] = new Employee();
        employees[1] = new Employee("Jane Smith", 6000, new Auditory[]{a2});
        employees[2] = new Laborant("Alice Johnson", 7000, new Auditory[]{a2, a3}, 2);
        employees[3] = new Laborant("Bob Brown", 5500, new Auditory[]{a1}, 1);
        employees[4] = new Laborant("Charlie Davis", 6500, new Auditory[]{a2}, 2);

        class EmployeesAnalyzer {
            public double getAverageSalary(Employee[] employees) {
                double totalSalary = 0;
                int emptyEmployeesCount = 0;

                for (Employee employee : employees) {
                    if (employee.getSalary() == 0) {
                        emptyEmployeesCount++;
                    }
                    totalSalary += employee.getSalary();
                }

                return totalSalary / (Employee.getTotalEmployees() - emptyEmployeesCount);
            }

            public Employee getHighestSalaryEmployee(Employee[] employees) {
                Employee topEmployee = employees[0];
                
                for (Employee employee : employees) {
                    if (employee.getSalary() > topEmployee.getSalary()) {
                        topEmployee = employee;
                    }
                }
                return topEmployee;
            }
        }

        EmployeesAnalyzer analyzer = new EmployeesAnalyzer();

        System.out.println("\n--- EMPLOYEE LIST ---");
        printEmployees(employees);

        System.out.println("\n--- ASSIGNMENT CHECK ---");
        for (Employee e: employees) {
            getAuditoriesForEmployee(e);
        }

        System.out.println("\n--- STATISTICS ---");
        Employee highestSalaryEmployee = analyzer.getHighestSalaryEmployee(employees);

        System.out.println("Highest salary employee: " + highestSalaryEmployee.getName() + " - $" + highestSalaryEmployee.getSalary());
        System.out.println("Average salary value: $" + analyzer.getAverageSalary(employees));
    }
}
