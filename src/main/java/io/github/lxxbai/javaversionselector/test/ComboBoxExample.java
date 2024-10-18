package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ComboBoxExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        List<String>versions=List.of("jdk8", "jdk9", "jdk10", "jdk11");
        // 创建 JFXComboBox
        ComboBox<String> comboBox = new ComboBox<>();
        // 设置提示文本作为描述
        comboBox.setPromptText("版本");
        comboBox.setEditable(false);
        // 添加选项
        comboBox.getItems().addAll(versions);
        VBox root = new VBox(15,  comboBox);
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
