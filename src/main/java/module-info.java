module org.example.employeemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.employeemanagementsystem to javafx.fxml;
    exports org.example.employeemanagementsystem;
}