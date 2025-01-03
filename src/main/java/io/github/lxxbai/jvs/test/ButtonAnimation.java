package io.github.lxxbai.jvs.test;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ButtonAnimation extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 固定的主按钮
        Button mainButton = new Button("触发按钮");
        mainButton.setLayoutX(100);
        mainButton.setLayoutY(100);

        // 动态动画按钮
        Button animatedButton = new Button("移动按钮");
        animatedButton.setLayoutX(100);
        animatedButton.setLayoutY(100);
        animatedButton.setVisible(false); // 初始隐藏

        // 布局
        Pane root = new Pane(mainButton, animatedButton);
        Scene scene = new Scene(root, 400, 300);

        // 主按钮点击事件
        mainButton.setOnAction(event -> {
            // 显示动画按钮
            animatedButton.setVisible(true);
            animatedButton.setOpacity(1.0); // 确保每次动画开始时不透明
            animatedButton.setTranslateX(0); // 重置位置

            // 创建移动动画
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), animatedButton);
//            translateTransition.setFromX(-20);
//            translateTransition.setFromY(-60);
//            translateTransition.setByX(200); // 向右移动200像素
            translateTransition.setToX(100);
            translateTransition.setToY(100);

            // 创建淡化动画
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), animatedButton);
            fadeTransition.setFromValue(1.0); // 初始透明度
            fadeTransition.setToValue(0.0);   // 最终透明度

            // 动画结束后隐藏按钮
            fadeTransition.setOnFinished(e -> animatedButton.setVisible(false));

            // 同时播放动画
            translateTransition.play();
            fadeTransition.play();
        });

        primaryStage.setTitle("按钮动画示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
