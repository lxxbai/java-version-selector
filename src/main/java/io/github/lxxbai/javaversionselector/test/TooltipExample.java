package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TooltipExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        JFXButton button = new JFXButton("Hover over me!");

        // 创建 Tooltip
        Tooltip tooltip = new Tooltip("This is a tooltip message!");
        
        // 将 Tooltip 绑定到按钮上
        Tooltip.install(button, tooltip);

        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Tooltip Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
