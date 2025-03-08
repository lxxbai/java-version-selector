package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXRadioButton;

public class JFXRadioButtonExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个垂直布局容器
        VBox vbox = new VBox();

        // 创建一个 ToggleGroup，用于将单选按钮分组
        ToggleGroup group = new ToggleGroup();

        // 创建 JFXRadioButton 实例
        JFXRadioButton radioButton1 = new JFXRadioButton("选项 1");
        JFXRadioButton radioButton2 = new JFXRadioButton("选项 2");
        JFXRadioButton radioButton3 = new JFXRadioButton("选项 3");

        // 将单选按钮添加到 ToggleGroup 中
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);
        radioButton3.setToggleGroup(group);

        // 默认选中第一个单选按钮
        radioButton1.setSelected(true);

        // 为单选按钮添加事件监听器
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                JFXRadioButton selectedRadioButton = (JFXRadioButton) newValue;
                System.out.println("选中的选项是: " + selectedRadioButton.getText());
            }
        });

        // 将单选按钮添加到垂直布局容器中
        vbox.getChildren().addAll(radioButton1, radioButton2, radioButton3);

        // 创建场景
        Scene scene = new Scene(vbox, 300, 200);

        // 设置舞台标题
        primaryStage.setTitle("JFXRadioButton 示例");
        // 将场景设置到舞台上
        primaryStage.setScene(scene);
        // 显示舞台
        primaryStage.show();
    }

    public static void main(String[] args) {
        // 启动 JavaFX 应用程序
        launch(args);
    }
}