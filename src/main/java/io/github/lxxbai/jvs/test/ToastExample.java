package io.github.lxxbai.jvs.test;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ToastExample extends Application {

    private static final int TOAST_DURATION = 3000;  // Toast 显示的时间（毫秒）
    private static final double TOAST_OPACITY = 0.9;  // Toast 的透明度

    @Override
    public void start(Stage primaryStage) {
        // 创建 StackPane 作为根布局
        StackPane root = new StackPane();

        // 创建按钮，点击后显示 Toast
        Button showToastButton = new Button("显示 Toast");
        showToastButton.setOnAction(event -> showCustomToast(root, "这是一条 Toast 消息"));

        // 将按钮添加到根布局
        VBox vBox = new VBox(showToastButton);
        vBox.setPadding(new Insets(50));
        root.getChildren().add(vBox);

        // 创建场景并显示舞台
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Toast 示例");
        primaryStage.show();
    }

    // 显示自定义 Toast
    private void showCustomToast(StackPane root, String message) {
        // 创建 Toast 消息框
        Label toastLabel = new Label(message);
        toastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 5px;");
        toastLabel.setOpacity(0);  // 初始时透明

        // 将 Toast 消息框添加到根布局
        root.getChildren().add(toastLabel);
        StackPane.setAlignment(toastLabel, javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setMargin(toastLabel, new Insets(20, 0, 0, 0));

        // 创建淡入动画
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toastLabel);
        fadeIn.setToValue(TOAST_OPACITY);
        fadeIn.play();

        // 创建淡出动画
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toastLabel);
        fadeOut.setToValue(0);

        // 设置 Toast 的显示时间
        PauseTransition pause = new PauseTransition(Duration.millis(TOAST_DURATION));
        pause.setOnFinished(event -> {
            fadeOut.play();
            fadeOut.setOnFinished(e -> root.getChildren().remove(toastLabel));  // 动画结束后移除 Toast
        });

        // 播放动画
        fadeIn.setOnFinished(event -> pause.play());
    }

    public static void main(String[] args) {
        launch(args);
    }
}