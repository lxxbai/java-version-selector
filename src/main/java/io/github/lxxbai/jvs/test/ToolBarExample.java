package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

public class ToolBarExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        ToolBar toolBar = new ToolBar();
        Button btn1 = new Button("Cut");
        Button btn2 = new Button("Copy");
        Button btn3 = new Button("Paste");
        toolBar.getItems().addAll(btn1, btn2, btn3);

        Scene scene = new Scene(toolBar, 300, 50);
        primaryStage.setTitle("Tool Bar Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}