package org.example.employeemanagementsystem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> tableName;

    @FXML
    private TableColumn<Employee, String> tableType;

    @FXML
    private TableColumn<Employee, Double> tableSalary;


    @FXML
    private Button addButton;

    @FXML
    private Button calculateButton;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField nameInput;


    @FXML
    private TextField hoursField;

    @FXML
    private TextField maxHoursField;

    @FXML
    private TextField rateField;


    @FXML
    void initialize() {

        hoursField.setVisible(false);
        rateField.setVisible(false);
        maxHoursField.setVisible(false);

        typeComboBox.getItems().addAll("Full-time", "Part-time", "Contractor");


        tableName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
        tableType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        tableSalary.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().calculateSalary()).asObject());



        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateFieldsVisibility(newValue);
        });

        employeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displaySelectedEmployeeData(newValue);
            }
        });

    }


    private void displaySelectedEmployeeData(Employee employee) {
        nameInput.setText(employee.name);


        if (employee instanceof FullTimeEmployee) {
            typeComboBox.setValue("Full-time");
        } else if (employee instanceof PartTimeEmployee) {
            typeComboBox.setValue("Part-time");
            PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
            rateField.setText(String.valueOf(partTimeEmployee.hourlyRate));
            hoursField.setText(String.valueOf(partTimeEmployee.hoursWorked));
        } else if (employee instanceof Contractor) {
            typeComboBox.setValue("Contractor");
            Contractor contractor = (Contractor) employee;
            rateField.setText(String.valueOf(contractor.hourRate));
            maxHoursField.setText(String.valueOf(contractor.maxHours));
        }


        updateFieldsVisibility(typeComboBox.getValue());
    }


    private void updateFieldsVisibility(String type) {
        hoursField.setVisible(false);
        rateField.setVisible(false);
        maxHoursField.setVisible(false);


        if ("Part-time".equals(type)) {
            hoursField.setVisible(true);
            rateField.setVisible(true);
        } else if ("Contractor".equals(type)) {
            maxHoursField.setVisible(true);
            rateField.setVisible(true);
        }

    }
    @FXML
    void onCalculateSalariesClicked() {
        saveEmployeeChanges();
        List<Employee> employees = employeeTable.getItems();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            double salary = employee.calculateSalary();
            System.out.println("Зарплата сотрудника " + employee.name + ": " + salary);
        }
        employeeTable.refresh();
    }
    @FXML
    private void saveEmployeeChanges() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) return;

        if (selectedEmployee instanceof PartTimeEmployee) {
            PartTimeEmployee partTime = (PartTimeEmployee) selectedEmployee;
            partTime.hourlyRate = Double.parseDouble(rateField.getText());
            partTime.hoursWorked = Double.parseDouble(hoursField.getText());
        } else if (selectedEmployee instanceof Contractor) {
            Contractor contractor = (Contractor) selectedEmployee;
            contractor.hourRate = Double.parseDouble(rateField.getText());
            contractor.maxHours = Double.parseDouble(maxHoursField.getText());
        }
    }

    @FXML
    void onAddEmployeeClicked() {
        String name = nameInput.getText();
        String type = typeComboBox.getValue();
        Employee newEmployee = null;

        if (name.isEmpty() || type == null) {
            return;
        }

        if ("Full-time".equals(type)) {
            double salary = 100000.0;
            newEmployee = new FullTimeEmployee(name, salary);
        } else if ("Part-time".equals(type)) {
            double hourlyRate = Double.parseDouble(rateField.getText());
            double hoursWorked = Double.parseDouble(hoursField.getText());
            newEmployee = new PartTimeEmployee(name, hourlyRate, hoursWorked);
        } else if ("Contractor".equals(type)) {
            double hourlyRate = Double.parseDouble(rateField.getText());
            double maxHours = Double.parseDouble(maxHoursField.getText());
            newEmployee = new Contractor(name, hourlyRate, maxHours);
        }


        if (newEmployee != null) {
            employeeTable.getItems().add(newEmployee);
        }
    }
}
