package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXToggleNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ToggleButtonGroupExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个 ToggleGroup 实例
        ToggleGroup group = new ToggleGroup();

        // 创建多个 ToggleButton，并将它们添加到同一个 ToggleGroup 中
        JFXToggleNode button1 = new JFXToggleNode("111");
        button1.setToggleGroup(group);
        JFXToggleNode button2 = new JFXToggleNode();
        button2.setText("Option 2");
        button2.setToggleGroup(group);

        ToggleButton button3 = new ToggleButton("Option 3");
        button3.setToggleGroup(group);

        // 使用 VBox 布局容器将按钮垂直排列
        VBox vBox = new VBox(button1, button2, button3);
        vBox.setSpacing(10); // 设置按钮之间的间距

        Scene scene = new Scene(vBox, 200, 150);
        primaryStage.setTitle("ToggleButton Group Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}