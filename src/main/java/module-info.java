module org.example.employeecalculatesalaryapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.employeecalculatesalaryapp to javafx.fxml;
    exports org.example.employeecalculatesalaryapp;
}