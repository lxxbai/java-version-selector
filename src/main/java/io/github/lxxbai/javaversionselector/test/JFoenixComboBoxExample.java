package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JFoenixComboBoxExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建带描述的标签
        Label descriptionLabel = new Label("请选择一个选项：");
        // 创建 JFXComboBox
        JFXComboBox<String> comboBox = new JFXComboBox<>();
        // 设置提示文本作为描述
        comboBox.setPromptText("未选择");
        // 添加选项
        comboBox.getItems().addAll("选项 1", "选项 2", "选项 3", "选项 4");
        // 创建一个按钮用于清除选择
        JFXButton clearButton = new JFXButton("清除选择");
        clearButton.setStyle("-fx-background-color: #FFCDD2; -fx-text-fill: black;");
        clearButton.setOnAction(event -> comboBox.getSelectionModel().clearSelection());
        // 布局
        HBox actionBox = new HBox(10, comboBox, clearButton);
        VBox root = new VBox(15, descriptionLabel, actionBox);
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
