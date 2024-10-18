
package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JFoenixTextExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建带描述的标签
        Label descriptionLabel = new Label("请选择一个选项：");
        // 创建 JFXComboBox
        JFXTextField ces = new JFXTextField();
        ces.setPromptText("ces");
        // 创建场景
        Scene scene = new Scene(ces, 400, 200);
        primaryStage.setTitle("JFoenix ComboBox 示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
