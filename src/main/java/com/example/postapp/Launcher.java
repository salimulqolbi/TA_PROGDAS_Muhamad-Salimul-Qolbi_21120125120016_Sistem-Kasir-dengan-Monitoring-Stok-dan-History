package com.example.postapp;

import com.example.postapp.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage)  {
        new MainView(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
