package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressBarExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个 ProgressBar
        ProgressBar progressBar = new ProgressBar(0.5); // 初始进度为50%

        // 创建布局并添加 ProgressBar
        VBox vbox = new VBox(progressBar);

        // 设置场景并显示舞台
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ProgressBar with Accessible Text Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}