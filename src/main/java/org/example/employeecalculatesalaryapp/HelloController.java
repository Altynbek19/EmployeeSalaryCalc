package org.example.employeecalculatesalaryapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField rateField;

    @FXML
    private TextField hoursField;

    @FXML
    private TextField maxHoursField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameColumn, typeColumn;

    @FXML
    private TableColumn<Employee, Double> salaryColumn;

    private ObservableList<Employee> employeeList;


    public void initialize() {
        // Инициализация списка сотрудников
        employeeList = FXCollections.observableArrayList();

        // Установка столбцов TableView
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        salaryColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().calculateSalary()));

        employeeTable.setItems(employeeList);

        // Инициализация ComboBox с типами сотрудников
        typeComboBox.setItems(FXCollections.observableArrayList("Full-time", "Part-time", "Contractor"));
    }

    // Добавить сотрудника на основе введённых данных
    @FXML
    private void addEmployee() {
        String name = nameField.getText();
        String type = typeComboBox.getValue();

        if (name.isEmpty() || type == null) {
            showAlert("Ошибка", "Пожалуйста, заполните все поля.");
            return;
        }

        try {
            if (type.equals("Full-time")) {
                double salary = Double.parseDouble(rateField.getText());
                employeeList.add(new FullTimeEmployee(name, salary));
            } else if (type.equals("Part-time")) {
                double hourlyRate = Double.parseDouble(rateField.getText());
                int hoursWorked = Integer.parseInt(hoursField.getText());
                employeeList.add(new PartTimeEmployee(name, hourlyRate, hoursWorked));
            } else if (type.equals("Contractor")) {
                double hourlyRate = Double.parseDouble(rateField.getText());
                int maxHours = Integer.parseInt(maxHoursField.getText());
                int hoursWorked = Integer.parseInt(hoursField.getText());  // Для подрядчиков тоже нужно учитывать отработанные часы
                employeeList.add(new Contractor(name, hourlyRate, maxHours, hoursWorked));
            }

            employeeTable.refresh();  // Обновить таблицу
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Пожалуйста, введите корректные числовые значения для ставки и часов.");
        }

        clearFields();  // Очистить поля ввода после добавления
    }


    // Рассчитать и обновить зарплаты
    @FXML
    private void calculateSalaries() {
        // Пересчитать и обновить зарплату для всех сотрудников
        for (Employee employee : employeeList) {
            employee.calculateSalary();
        }

        // Обновить таблицу для отображения обновленных зарплат
        employeeTable.refresh();

        // Показать уведомление о том, что зарплаты обновлены
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Зарплаты обновлены");
        alert.setHeaderText(null);
        alert.setContentText("Зарплаты были обновлены.");
        alert.showAndWait();
    }


    // Удалить выбранного сотрудника
    @FXML
    private void removeEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeList.remove(selectedEmployee);
        } else {
            showAlert("Ошибка", "Пожалуйста, выберите сотрудника для удаления.");
        }
    }

    // Показать окно с сообщением
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Очистить поля ввода после добавления сотрудника
    private void clearFields() {
        nameField.clear();
        typeComboBox.getSelectionModel().clearSelection();
        rateField.clear();
        hoursField.clear();
        maxHoursField.clear();
    }
}
