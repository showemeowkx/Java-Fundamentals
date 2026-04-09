package managers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    public Employee findEmployeeByName(String name) {
        for (Employee employee : employeesList) {
            if (employee.getName().equalsIgnoreCase(name)) {
                return employee;
            }
        }

        return null;
    }

    public Boolean compareEmployeesSalary(Employee e1, Employee e2) {
        if (!employeesList.contains(e1) || !employeesList.contains(e2)) {
            throw new IllegalArgumentException("Some employees are not in the collection.");
        }

        return e1.getSalary() > e2.getSalary();
    }

    public double getAverageSalary() {
        double totalSalary = 0;
        int validEmployeesCount = 0;

        for (Employee employee : employeesList) {
            if (employee.getSalary() > 0) {
                totalSalary += employee.getSalary();
                validEmployeesCount++;
            }
        }

        if (validEmployeesCount == 0) return 0;

        return totalSalary / validEmployeesCount;
    }

    public Collection<Employee> filterEmployeesByAuditoriesQty(int min) {
        Collection<Employee> returnCollection = new ArrayList<>();

        for (Employee employee : employeesList) {
            int qty = (employee.getAuditories() != null) ? employee.getAuditories().length : 0;
            if (qty >= min) {
                returnCollection.add(employee);
            }
        }

        return returnCollection;
    }

    public List<Employee> sortEmployeesBySalaryAC(int sortMethod) {
        if (sortMethod != 0 && sortMethod != 1) {
            throw new IllegalArgumentException("Invalid sort method argument. Use 0 for DESC and 1 for ASC.");
        }

        List<Employee> sortedList = new ArrayList<>(employeesList);
        Collections.sort(sortedList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                if (e1.getSalary() == e2.getSalary()) {
                    return 0;
                }
                if (sortMethod == 1) {
                    return e1.getSalary() > e2.getSalary() ? 1 : -1;
                } else {
                    return e1.getSalary() > e2.getSalary() ? -1 : 1;
                }
            }
        });

        return sortedList;
    }

    public List<Employee> sortEmployeesBySalaryLambda(int sortMethod) {
        if (sortMethod != 0 && sortMethod != 1) {
            throw new IllegalArgumentException("Invalid sort method argument. Use 0 for DESC and 1 for ASC.");
        }

        List<Employee> sortedList = new ArrayList<>(employeesList);
        if (sortMethod == 1) {
            sortedList.sort((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        } else {
            sortedList.sort((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        }

        return sortedList;
    }

    public List<Employee> sortEmployeesBySalaryMR(int sortMethod) {
        if (sortMethod != 0 && sortMethod != 1) {
            throw new IllegalArgumentException("Invalid sort method argument. Use 0 for DESC and 1 for ASC.");
        }

        List<Employee> sortedList = new ArrayList<>(employeesList);
        if (sortMethod == 1) {
            sortedList.sort(Comparator.comparingDouble(Employee::getSalary));
        } else {
            sortedList.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
        }

        return sortedList;
    }
}