package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JFXDecoratorButtonRightExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. 创建主内容区域
        BorderPane root = new BorderPane();
        Label content = new Label("This is the main content area.");
        root.setCenter(content);

        // 2. 创建自定义按钮
        Button customButton = new Button("Custom");
        customButton.setOnAction(e -> System.out.println("Custom button clicked!"));

        // 3. 创建一个 HBox 来容纳标题栏的内容
        HBox titleBarContent = new HBox();
        Label titleLabel = new Label("My Custom Window");

        // 4. 添加一个 Region 占位符，让按钮靠右对齐
        Region spacer = new Region();

        // 5. 将 Label 和按钮添加到 HBox，并调整顺序
        titleBarContent.getChildren().addAll(titleLabel, spacer, customButton);

        // 6. 使用 JFXDecorator 包装主内容，并设置自定义标题栏
        JFXDecorator decorator = new JFXDecorator(primaryStage, root, false, true, true);
        decorator.setCustomMaximize(true);  // 启用最大化功能
//        decorator.getChildren().add(customButton);
        // 7. 创建场景并显示
        Scene scene = new Scene(decorator, 600, 400);
        scene.getStylesheets().addAll(
                ResourceUtil.toExternalForm("css/jf-all.css")
//                ,ResourceUtil.toExternalForm("css/jfoenix-main-demo.css")
//                BootstrapFX.bootstrapFXStylesheet()
        );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
