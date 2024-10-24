package io.github.lxxbai.javaversionselector.test;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个 BorderPane 布局容器
        BorderPane root = new BorderPane();

        // 创建一个 HBox 用于放置按钮
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(10));
        topBox.setSpacing(10);

        // 创建一个按钮
        Button closeButton = new Button("关闭");
        closeButton.setOnAction(e -> {
            primaryStage.close();
        });

        // 将按钮添加到 HBox 中
        topBox.getChildren().add(closeButton);

        // 将 HBox 设置为 BorderPane 的顶部
        root.setTop(topBox);

        // 设置 HBox 的对齐方式为右对齐
        BorderPane.setAlignment(topBox, Pos.BASELINE_RIGHT);

        // 创建一个场景
        Scene scene = new Scene(root, 600, 400);

        // 设置舞台（窗口）的标题
        primaryStage.setTitle("JavaFX 示例");

        // 设置舞台的场景
        primaryStage.setScene(scene);

        // 显示舞台
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}