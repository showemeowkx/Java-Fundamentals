package employee;
import auditory.Auditory;

public class Employee {
    private static int totalEmployees = 0;
    private static int nextId = 1;

    private int id;
    private String name;
    private double salary;
    private Auditory[] auditories;

    public Employee() {
        id = nextId++;
        this.name = "Unknown";
        this.salary = 0;
        this.auditories = null;
        totalEmployees++;
    }

    public Employee(String name, double salary, Auditory[] auditories) {
        id = nextId++;
        this.name = name;
        this.salary = salary;
        this.auditories = auditories;
        totalEmployees++;
    }

    public int getId() {
        return id;
    }

    public String getEmployeeData() {
        return "ID: " + id + ", Name: " + name + ", Salary: $" + salary;
    }

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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.salary = salary;
    }

    public Auditory[] getAuditories() {
        return auditories;
    }

    public void setAuditories(Auditory[] auditories) {
        int n = auditories.length;

        if (n == 0) {
            this.auditories = null;
            return;
        }

        int[] auditoryIds = new int[n];

        for (int i = 0; i < n; i++) {
            auditoryIds[i] = auditories[i].getId();
        }

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if(auditories[i].getId() == auditories[j].getId()) {
                    throw new IllegalArgumentException("Cannot set auditories: Duplicate auditories found.");
                }
            }
        }

        this.auditories = auditories;
    }

    public void clearAuditories() {
        auditories = null;
    }

    public void addAuditory(Auditory auditory) {
        if (auditories == null) {
            auditories = new Auditory[1];
            auditories[0] = auditory;
            return;
        }

        for (Auditory a: auditories ) {
            if (a.getId() == auditory.getId()) {
                throw new IllegalArgumentException("This auditory already assigned to this employee.");
            }
        }

        int n = auditories.length;

        Auditory[] newAuditories = new Auditory[n + 1];

        for (int i = 0; i < n; i++) {
            newAuditories[i] = auditories[i];
        }

        newAuditories[n] = auditory;
        auditories = newAuditories;
    }

    public void sortAuditoriesById() {
        if (auditories == null) {
            return;
        }

        int n = auditories.length;
        Auditory[] sortedAuditories = auditories.clone();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sortedAuditories[j].getId() > sortedAuditories[j + 1].getId()) {
                    Auditory temp = sortedAuditories[j];
                    sortedAuditories[j] = sortedAuditories[j + 1];
                    sortedAuditories[j + 1] = temp;
                }
            }
        }

        auditories = sortedAuditories;
    }

    public void giveRaise(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Raise amount cannot be negative.");
        }
        salary += amount;
    }

    public void giveRaise(double percentage, boolean isPercentage) {
        if (isPercentage) {
            if (percentage < 0) {
                throw new IllegalArgumentException("Raise percentage cannot be negative.");
            }

            double amount = salary * (percentage / 100);
            salary += amount;
        }
    }
}