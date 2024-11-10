package io.github.lxxbai.javaversionselector.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class SvgExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // SVG 路径数据
        String svgData = "M 25 25 L 55 25 L 55 35 L 35 35 L 35 55 L 25 55 Z" +
                         "M 145 25 L 175 25 L 175 55 L 165 55 L 165 35 L 145 35 Z" +
                         "M 25 145 L 35 145 L 35 165 L 55 165 L 55 175 L 25 175 Z" +
                         "M 145 175 L 175 175 L 175 145 L 165 145 L 165 165 L 145 165 Z";

        // 创建 SVGPath 对象
        SVGPath svgPath = new SVGPath();
        svgPath.setContent(svgData);
        // 设置填充颜色
        svgPath.setFill(javafx.scene.paint.Color.BLACK);

        // 创建 StackPane 并添加 SVGPath
        StackPane root = new StackPane();
        root.getChildren().add(svgPath);

        // 创建 Scene 并设置到 Stage
        Scene scene = new Scene(root, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SVG Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}