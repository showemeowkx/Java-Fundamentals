import javax.swing.SwingUtilities;

import auditory.Auditory;
import employee.Employee;
import employee.Laborant;
import managers.AuditoriesMap;
import managers.EmployeesCollection; 

public class Main {
    public static void main(String[] args) {
        Auditory a1 = new Auditory(1, "Informatics", 70);
        Auditory a2 = new Auditory(2, "Mathematics", 30);
        Auditory a3 = new Auditory(1, "Physics", 50);

        Employee emp0 = new Employee();
        Employee emp1 = new Employee("Jane Smith", 6000, new Auditory[]{a2});
        Employee lab1 = new Laborant("Alice Johnson", 7000, new Auditory[]{a2, a3}, 2);
        Employee lab2 = new Laborant("Bob Brown", 5500, new Auditory[]{a1}, 1);
        Employee lab3 = new Laborant("Charlie Davis", 6500, new Auditory[]{a1, a2}, 2);

        EmployeesCollection collection = new EmployeesCollection(true); 
        collection.addEmployee(emp0);
        collection.addEmployee(emp1);
        collection.addEmployee(lab1);
        collection.addEmployee(lab2);
        collection.addEmployee(lab3);
        
        AuditoriesMap auditoriesMap = new AuditoriesMap();
        auditoriesMap.addAuditory(a1);
        auditoriesMap.addAuditory(a2);
        auditoriesMap.addAuditory(a3);

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(collection, auditoriesMap);
            frame.setVisible(true);
        });
    }
}