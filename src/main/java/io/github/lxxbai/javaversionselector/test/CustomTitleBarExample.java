package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXDecorator;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomTitleBarExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 主内容区域
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 300);

        // 创建自定义标题栏的控件
        Label titleLabel = new Label("My Application");

        // 配置按钮
        Button settingsButton = new Button("⚙️");
        settingsButton.setOnAction(event -> System.out.println("Settings button clicked"));

        // 最小化按钮
        Button minimizeButton = new Button("━");
        minimizeButton.setOnAction(event -> primaryStage.setIconified(true));

        // 关闭按钮
        Button closeButton = new Button("X");
        closeButton.setOnAction(event -> primaryStage.close());

        // 用 HBox 布局标题栏元素
        HBox titleBar = new HBox(10, titleLabel, settingsButton, minimizeButton, closeButton);
        titleBar.setStyle("-fx-alignment: center-right; -fx-padding: 5; -fx-background-color: #2C3E50;");

        // 设置标题栏在左侧对齐，按钮在右侧对齐
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        // 创建 JFXDecorator 并将自定义标题栏和内容区域整合
        JFXDecorator decorator = new JFXDecorator(primaryStage, root, true, true, false);
        // 将自定义标题栏设置为图形内容
        decorator.setGraphic(titleBar);
        // 设置场景和样式
        Scene decoratorScene = new Scene(decorator, 400, 300);
        decoratorScene.getStylesheets().add(  ResourceUtil.toExternalForm("css/jf-all.css"));

        primaryStage.setScene(decoratorScene);
        primaryStage.setTitle("Custom Title Bar with Settings");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
