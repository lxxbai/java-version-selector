package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class JFoenixComboBoxExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        List<String> versions = List.of("--所有版本--", "jdk8", "jdk9", "jdk10", "jdk11");
        // 创建 JFXComboBox
        JFXComboBox<String> comboBox = new JFXComboBox<>();
        comboBox.setEditable(false);
        // 添加选项
        comboBox.getItems().addAll(versions);
        comboBox.setValue("--所有版本--");
        VBox root = new VBox(15, comboBox);
        root.setPadding(new Insets(20));

        // 创建场景
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("JFoenix ComboBox 示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
