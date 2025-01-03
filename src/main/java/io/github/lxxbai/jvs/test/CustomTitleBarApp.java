package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomTitleBarApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. 隐藏默认窗口装饰
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // 2. 创建自定义标题栏
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #2C3E50; -fx-padding: 5px;");
        titleBar.setSpacing(10);

        Label titleLabel = new Label("My Custom Window");
        titleLabel.setStyle("-fx-text-fill: white;");

        // 3. 创建最小化按钮
        Button minimizeButton = new Button("_");
        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));

        // 4. 创建最大化/恢复按钮
        Button maximizeButton = new Button("□");
        maximizeButton.setOnAction(e -> {
            if (primaryStage.isMaximized()) {
                primaryStage.setMaximized(false);
            } else {
                primaryStage.setMaximized(true);
            }
        });

        // 5. 创建关闭按钮
        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> primaryStage.close());

        // 6. 自定义按钮
        Button customButton = new Button("Custom");
        customButton.setOnAction(e -> System.out.println("Custom button clicked!"));

        // 将按钮添加到标题栏
        titleBar.getChildren().addAll(titleLabel, customButton, minimizeButton, maximizeButton, closeButton);

        // 7. 设置主布局
        BorderPane root = new BorderPane();
        root.setTop(titleBar);

        // 示例内容区域
        Label content = new Label("This is the content area.");
        root.setCenter(content);

        // 8. 创建场景并显示
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
