package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JFXDecoratorExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建 VBox 布局
        VBox vBox = new VBox();
        // 创建按钮
        Button button = new Button("Click Me");
        vBox.getChildren().add(button);

        // 创建 JFXDecorator 并设置内容
        JFXDecorator decorator = new JFXDecorator(primaryStage, vBox);
        decorator.setText("My Custom Window");
        // 可以设置图标
        decorator.setGraphic(new SVGGlyph(""));
        // 创建场景
        Scene scene = new Scene(decorator, 400, 300);
        // 设置舞台
        primaryStage.setTitle("JFXDecorator Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}