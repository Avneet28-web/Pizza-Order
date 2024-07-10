module com.example.javaavneet {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.javaavneet to javafx.fxml;
    exports com.example.javaavneet;
}