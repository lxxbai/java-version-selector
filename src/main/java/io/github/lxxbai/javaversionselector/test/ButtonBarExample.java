package io.github.lxxbai.javaversionselector.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonBarExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个 ButtonBar
        ButtonBar buttonBar = new ButtonBar();

        // 创建一些按钮并添加到 ButtonBar 中
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        Button applyButton = new Button("Apply");

        buttonBar.getButtons().addAll(okButton, cancelButton, applyButton);

        // 创建一个 VBox 容器
        VBox vBox = new VBox(buttonBar);

        // 创建场景
        Scene scene = new Scene(vBox, 300, 200);

        // 设置舞台
        primaryStage.setTitle("ButtonBar Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}