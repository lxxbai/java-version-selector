package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RoundedImageView extends Application {
    @Override
    public void start(Stage primaryStage) {
        Image image = new Image("/pic/theme/dark.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        // 创建一个矩形并设置圆角
        Rectangle clip = new Rectangle(150, 150);
        clip.setArcWidth(8);  // 圆角宽度
        clip.setArcHeight(8); // 圆角高度
        imageView.setClip(clip); // 设置裁剪

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX 圆角 ImageView");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
