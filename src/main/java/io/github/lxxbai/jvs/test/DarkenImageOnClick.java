package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DarkenImageOnClick extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 加载图片
        Image image = new Image("/pic/theme/dark.png");
        ImageView imageView = new ImageView(image);

        // 创建 ColorAdjust 对象
        ColorAdjust colorAdjust = new ColorAdjust();

        // 为 ImageView 添加点击事件处理程序
        imageView.setOnMouseClicked(event -> {
            // 降低亮度使图片变暗
            colorAdjust.setBrightness(-0.3);
            imageView.setEffect(colorAdjust);
        });

        // 创建一个 StackPane 并将 ImageView 添加到其中
        StackPane root = new StackPane(imageView);

        // 创建场景并设置到舞台上
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("点击后图片变暗");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}