package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.component.LJFXDecorator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JFXDecoratorButtonRightExample1 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. 创建主内容区域
        BorderPane root = new BorderPane();
        Label content = new Label("This is the main content area.");
        root.setCenter(content);
        // 6. 使用 JFXDecorator 包装主内容，并设置自定义标题栏
        LJFXDecorator decorator = new LJFXDecorator(primaryStage, root, false, true, true);
        // 2. 创建自定义按钮
        JFXButton btnSettings = new JFXButton();
        btnSettings.setOnAction(e -> System.out.println("Custom button clicked!"));
        SVGGlyph settings = SVGGlyphLoader.loadGlyph(ResourceUtil.getUrl("svg/1-settings.svg"));
        settings.setFill(Color.WHITE);
        settings.setSize(15, 15);
        btnSettings.setRipplerFill(Color.WHITE);
        btnSettings.setGraphic(settings);
        decorator.addButton(btnSettings, 1);
        // 7. 创建场景并显示
        Scene scene = new Scene(decorator, 600, 400);
        scene.getStylesheets().addAll(
                ResourceUtil.toExternalForm("css/jf-all.css")
        );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
