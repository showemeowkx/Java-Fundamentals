package managers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import employee.Employee;

public class EmployeesCollection {
    private Collection<Employee> employeesList;

    public EmployeesCollection() {
        employeesList = new ArrayList<>();
    }

    public EmployeesCollection(boolean uniqueOnly) {
        if (uniqueOnly) {
            employeesList = new HashSet<>(); 
        } else {
            employeesList = new ArrayList<>();
        }
    }

    public void addEmployee(Employee e) {
        boolean wasAdded = employeesList.add(e);
        
        if (!wasAdded) {
            throw new IllegalArgumentException("This employee is already in the collection.");
        }
    }
    
    public Collection<Employee> getEmployeesList() {
        return employeesList;
    }

    public Employee findEmployeeById(int id) {
        for (Employee employee : employeesList) {
            if (employee.getId() == id) {
                return employee;
            }
        }

        return null;
    }

    public List<Employee> sortEmployeesById() {
        List<Employee> sortedList = new ArrayList<>(employeesList);
        sortedList.sort(Comparator.comparingInt(Employee::getId));

        return sortedList;
    }
}