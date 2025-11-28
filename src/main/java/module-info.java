module com.example.postapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
//    requires com.example.postapp;


    opens com.example.postapp to javafx.fxml;
    exports com.example.postapp;
    exports com.example.postapp.controller;
    opens com.example.postapp.controller to javafx.fxml;
    exports com.example.postapp.view;
    opens com.example.postapp.view to javafx.fxml;
}