package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXSpinner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ProgressIndicatorExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个ProgressIndicator，默认为不确定模式
        JFXSpinner progressIndicator = new JFXSpinner();
        progressIndicator.setRadius(10);
        StackPane root = new StackPane();
        root.getChildren().add(progressIndicator);

        Scene scene = new Scene(root, 200, 200);
        
        primaryStage.setTitle("ProgressIndicator Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}