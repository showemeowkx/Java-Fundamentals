import javax.swing.SwingUtilities;
import auditory.Auditory;
import employee.Employee;
import employee.Laborant;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Auditory a1 = new Auditory(1, "Informatics", 70);
        Auditory a2 = new Auditory(2, "Mathematics", 30);
        Auditory a3 = new Auditory(1, "Physics", 50);

        List<Auditory> auditoriesList = new ArrayList<>();
        auditoriesList.add(a1);
        auditoriesList.add(a2);
        auditoriesList.add(a3);

        Employee emp1 = new Employee("Jane Smith", 6000, new Auditory[]{a1, a2});
        Employee lab1 = new Laborant("Alice Johnson", 7000, new Auditory[]{a2, a3}, 2);
        Employee lab2 = new Laborant("Bob Brown", 5500, new Auditory[]{a1, a3}, 1);

        List<Employee> employeesList = new ArrayList<>();
        employeesList.add(emp1);
        employeesList.add(lab1);
        employeesList.add(lab2);

        SwingUtilities.invokeLater(() -> {
            AnimationWindow frame = new AnimationWindow(employeesList, auditoriesList);
            frame.setVisible(true);
        });
    }
}