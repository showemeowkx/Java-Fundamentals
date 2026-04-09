import java.util.Collection;
import java.util.List;
import java.util.Map;

import auditory.Auditory;
import employee.Employee;
import employee.Laborant;
import managers.AuditoriesMap;
import managers.EmployeesCollection; 

public class Main {

    private static void printEmployees(Collection<Employee> employees) {
        int index = 1;
        for (Employee employee : employees) {
            System.out.println(index + ". " + employee.getEmployeeData());
            index++;
        }
    }

    private static void printAuditoriesMap(Map<Integer, Auditory> map) {
        if (map.isEmpty()) {
            System.out.println("[Map is empty]");
            return;
        }
        for (Map.Entry<Integer, Auditory> entry : map.entrySet()) {
            System.out.println("Key ID: " + entry.getKey() + 
                " -> " + entry.getValue().getForSubject() + 
                " (Capacity: " + entry.getValue().getCapacity() + ")");
        }
    }

    public static void main(String[] args) {
        Auditory a1 = new Auditory(1, "Informatics", 70);
        Auditory a2 = new Auditory(2, "Mathematics", 30);
        Auditory a3 = new Auditory(1, "Physics", 50);

        Employee emp0 = new Employee();
        Employee emp1 = new Employee("Jane Smith", 6000, new Auditory[]{a2});
        Employee lab1 = new Laborant("Alice Johnson", 7000, new Auditory[]{a2, a3}, 2);
        Employee lab2 = new Laborant("Bob Brown", 5500, new Auditory[]{a1}, 1);
        Employee lab3 = new Laborant("Charlie Davis", 6500, new Auditory[]{a1, a2}, 2);

        System.out.println("=== TASK 1: EMPLOYEE COLLECTION (UNIQUE ONLY) ===");
        EmployeesCollection collection = new EmployeesCollection(true); 
        
        collection.addEmployee(emp0);
        collection.addEmployee(emp1);
        collection.addEmployee(lab1);
        collection.addEmployee(lab2);
        collection.addEmployee(lab3);

        System.out.println("--- INITIAL EMPLOYEE LIST ---");
        printEmployees(collection.getEmployeesList());

        System.out.println("\nAttempting to add a duplicate:");
        try {
            collection.addEmployee(emp1); 
        } catch (IllegalArgumentException e) {
            System.out.println("Error caught: " + e.getMessage());
        }

        System.out.println("\n--- SEARCH AND FILTER METHODS TEST ---");
        
        Employee found = collection.findEmployeeByName("Alice Johnson");
        System.out.println("Search by name 'Alice Johnson': " + 
            (found != null ? "Found - ID " + found.getId() + ", Salary: $" + found.getSalary() : "Not found"));

        System.out.println("\nFiltering: Employees with 2 or more auditories:");
        Collection<Employee> filtered = collection.filterEmployeesByAuditoriesQty(2);
        printEmployees(filtered);

        System.out.println("\n--- STATISTICS TEST ---");
        System.out.println("Salary comparison (Alice vs Bob): " + 
            (collection.compareEmployeesSalary(lab1, lab2) ? "Alice earns more" : "Bob earns more"));
        
        System.out.println("Average salary in collection: $" + collection.getAverageSalary());

        System.out.println("\n--- SALARY SORTING TEST (DESC) ---");
        List<Employee> sortedStaff = collection.sortEmployeesBySalaryMR(0); 
        printEmployees(sortedStaff);

                System.out.println("\n\n=== TASK 2: AUDITORIES MAP ===");
        AuditoriesMap auditoriesMapManager = new AuditoriesMap();

        System.out.println("--- ADDING AUDITORIES TO MAP ---");
        auditoriesMapManager.addAuditory(a1);
        auditoriesMapManager.addAuditory(a2);
        auditoriesMapManager.addAuditory(a3);
        printAuditoriesMap(auditoriesMapManager.getAuditoriesMap());

        System.out.println("\n--- TOTAL CAPACITY ---");
        System.out.println("Total capacity of all auditories: " + auditoriesMapManager.getTotalCapacity() + " students");

        System.out.println("\n--- FILTERING (ID >= 2) ---");
        Map<Integer, Auditory> filteredMap = auditoriesMapManager.filterAuditoriesByNumber(2);
        printAuditoriesMap(filteredMap);

        System.out.println("\n--- SORTING MAP BY CAPACITY (ASC) ---");
        Map<Integer, Auditory> sortedMap = auditoriesMapManager.sortAuditoriesByCapacityLambda(1);
        printAuditoriesMap(sortedMap);

        System.out.println("\n--- REMOVAL TEST ---");
        System.out.println("Removing 'Mathematics' auditory...");
        auditoriesMapManager.removeAuditoryBySubject("Mathematics");
        printAuditoriesMap(auditoriesMapManager.getAuditoriesMap());
    }
}