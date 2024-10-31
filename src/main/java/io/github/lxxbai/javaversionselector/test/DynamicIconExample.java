package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DynamicIconExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 加载动态图标（GIF）
        Image image = ResourceUtil.toImage("pic/downloading.gif");
        ImageView imageView = new ImageView(image);
        JFXButton button = new JFXButton();
        button.setGraphic(imageView);
        // 创建布局并添加图标
        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("动态图标示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
