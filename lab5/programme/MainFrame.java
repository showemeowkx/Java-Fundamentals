import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableModel;

import auditory.Auditory;

import java.util.Collection;

import employee.Employee;
import employee.Laborant;
import managers.AuditoriesMap;
import managers.EmployeesCollection;

public class MainFrame extends JFrame {
    private EmployeesCollection collection;
    private AuditoriesMap auditoriesMap;
    private JTable table;
    private DefaultTableModel tableModel;
    private JList<Auditory> auditoryList;
    private Timer simulationTimer;

    public MainFrame(EmployeesCollection collection, AuditoriesMap auditories) {
        this.collection = collection;
        this.auditoriesMap = auditories;

        setTitle("Staff Management System");
        setSize(800, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initTable();
        initAuditoriesList();
        initPanel();

        updateTable();
    }

    public void initTable() {
        String[] columns = {"ID", "Name", "Salary", "Role", "Auditories", "Master Key"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable() {
        tableModel.setRowCount(0);

        Collection<Employee> currentList = collection.sortEmployeesById();

        for (Employee e : currentList) {
            String role = e.getClass().getSimpleName();
            String auditoriesText = "-";
            String masterKeyText = "-";

            if (e.getAuditories() != null) {
                auditoriesText = "";
                for (Auditory a : e.getAuditories()) {
                    String auditoryId = String.valueOf(a.getId()) + " ";
                    auditoriesText = auditoriesText + auditoryId;
                }
            }
            
            if (e instanceof Laborant) {
                Laborant lab = (Laborant) e;
                int key = lab.getMasterKeyId();
                if (key >= 1) {
                    masterKeyText = String.valueOf(key); 
                }
            }

            Object[] row = {
                e.getId(),
                e.getName(),
                String.format("$%.2f", e.getSalary()),
                role,
                auditoriesText,
                masterKeyText
            };
            
            tableModel.addRow(row);
        }
    }

    public JList<Auditory> initAuditoriesList() {
        Collection<Auditory> values = auditoriesMap.getAuditoriesMap().values();
        Auditory[] availableAuditories = values.toArray(new Auditory[0]);

        auditoryList = new JList<>(availableAuditories);
        auditoryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        return auditoryList;
    }

    public void initPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton simulationButton = new JButton("Start Simulation");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmployeeDialog(null);
            }
        });

        editButton.addActionListener(e -> {
            Employee empToEdit = getSelectedEmployee();
            if (empToEdit != null) {
                openEmployeeDialog(empToEdit);
            }
        });

        deleteButton.addActionListener(this::handleDeleteAction);

        simulationTimer = new Timer(2000, e -> {
            for (Employee emp : collection.getEmployeesList()) {
                emp.giveRaise(5, true);
            }
            updateTable(); 
        });

        simulationButton.addActionListener(e -> {
            if (simulationTimer.isRunning()) {
                simulationTimer.stop();
                simulationButton.setText("Start Simulation");
            } else {
                simulationTimer.start();
                simulationButton.setText("Stop Simulation");
            }
        });

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(deleteButton);
        topPanel.add(simulationButton);

        add(topPanel, BorderLayout.NORTH);
    }

    private void handleDeleteAction(ActionEvent e) {
        Employee empToDelete = getSelectedEmployee();
        if (empToDelete != null) {
            collection.getEmployeesList().remove(empToDelete);
            updateTable();
        }
    }

    private Employee getSelectedEmployee() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        return collection.findEmployeeById(id);
    }

    private void openEmployeeDialog(Employee employeeToEdit) {
        boolean isEditMode = (employeeToEdit != null);
        String title = isEditMode ? "Edit Employee" : "Add Employee";

        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(350, 400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel nameLabel = new JLabel(" Name:");
        JTextField nameField = new JTextField(isEditMode ? employeeToEdit.getName() : "");

        JLabel salaryLabel = new JLabel(" Salary:");
        JTextField salaryField = new JTextField(isEditMode ? String.valueOf(employeeToEdit.getSalary()) : "");

        JLabel roleLabel = new JLabel(" Role:");
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Employee", "Laborant"});

        JLabel auditoriesLabel = new JLabel(" Auditories:");
        JList<Auditory> auditoryList = initAuditoriesList();
        JScrollPane listScroller = new JScrollPane(auditoryList);

        JLabel keyLabel = new JLabel(" Master key:");
        JTextField keyField = new JTextField();

        if (isEditMode) {
            boolean isLaborant = employeeToEdit instanceof Laborant;
            roleBox.setSelectedItem(isLaborant ? "Laborant" : "Employee");
            roleBox.setEnabled(false);
            
            if (isLaborant) {
                keyField.setText(String.valueOf(((Laborant) employeeToEdit).getMasterKeyId()));
            }
            keyField.setEnabled(isLaborant);
        } else {
            keyField.setEnabled(false);
            roleBox.addActionListener(e -> {
                boolean laborantSelected = roleBox.getSelectedItem().equals("Laborant");
                keyField.setEnabled(laborantSelected);
                if (!laborantSelected) keyField.setText("");
            });
        }

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                Auditory[] selectedAuditories = auditoryList.getSelectedValuesList().toArray(new Auditory[0]);
                Integer masterKey = keyField.getText().isEmpty() ? null : Integer.parseInt(keyField.getText());

                Employee targetEmployee = isEditMode ? employeeToEdit : null;

                if (!isEditMode) {
                    if (roleBox.getSelectedItem().equals("Laborant")) {
                        targetEmployee = new Laborant();
                    } else {
                        targetEmployee = new Employee();
                    }
                }

                targetEmployee.setName(name);
                targetEmployee.setSalary(salary);
                targetEmployee.setAuditories(selectedAuditories);

                if (targetEmployee instanceof Laborant) {
                    ((Laborant) targetEmployee).setMasterKeyId(masterKey != null ? masterKey : -1);
                }

                if (!isEditMode) {
                    collection.addEmployee(targetEmployee);
                }

                updateTable();
                dialog.dispose();
                
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(nameLabel);       dialog.add(nameField);
        dialog.add(salaryLabel);     dialog.add(salaryField);
        dialog.add(roleLabel);       dialog.add(roleBox);
        dialog.add(auditoriesLabel); dialog.add(listScroller);
        dialog.add(keyLabel);        dialog.add(keyField);
        dialog.add(saveButton);      dialog.add(cancelButton);

        dialog.setVisible(true);
    }
}