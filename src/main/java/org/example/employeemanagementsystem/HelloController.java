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

        // Настройка колонок таблицы
        tableName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
        tableType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        tableSalary.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().calculateSalary()).asObject());

        // Устанавливаем обработчик выбора
        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateFieldsVisibility(newValue);
        });

    }

    private void updateFieldsVisibility(String type) {
        // Скрыть все дополнительные поля
        hoursField.setVisible(false);
        rateField.setVisible(false);
        maxHoursField.setVisible(false);

        // Показать нужные поля в зависимости от выбора
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
        List<Employee> employees = employeeTable.getItems();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            double salary = employee.calculateSalary();
            System.out.println("Зарплата сотрудника " + employee.name + ": " + salary);
        }
    }

    @FXML
    void onAddEmployeeClicked() {
        String name = nameInput.getText();
        String type = typeComboBox.getValue();
        Employee newEmployee = null;

        if (name.isEmpty() || type == null) {
            // Валидация
            return;
        }

        if ("Full-time".equals(type)) {
            double salary = 100000.0;
            newEmployee = new FullTimeEmployee(name, salary);
        } else if ("Part-time".equals(type)) {
            // Добавление сотрудника Part-time
            double hourlyRate = Double.parseDouble(rateField.getText());
            double hoursWorked = Double.parseDouble(hoursField.getText());
            newEmployee = new PartTimeEmployee(name, hourlyRate, hoursWorked);
        } else if ("Contractor".equals(type)) {
            // Добавление сотрудника Contractor
            double hourlyRate = Double.parseDouble(rateField.getText());
            double maxHours = Double.parseDouble(maxHoursField.getText());
            newEmployee = new Contractor(name, hourlyRate, maxHours);
        }

        // Добавление сотрудника в таблицу
        if (newEmployee != null) {
            employeeTable.getItems().add(newEmployee);
        }
    }
}
