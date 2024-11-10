package io.github.lxxbai.javaversionselector.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.concurrent.Task;

public class ProgressBarExample extends Application {

    private ProgressBar progressBar = new ProgressBar(0);
    private Label label = new Label("0%");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // 创建一个垂直布局容器
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        // 添加进度条和标签到布局中
        vBox.getChildren().addAll(progressBar, label);

        // 设置场景
        Scene scene = new Scene(vBox, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.setTitle("下载和安装进度");
        primaryStage.show();

        // 创建一个后台任务来模拟下载和安装过程
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateProgress(0, 100); // 初始化进度
                for (int i = 1; i <= 50; i++) {
                    Thread.sleep(100); // 模拟下载过程
                    updateProgress(i, 50);
                    updateMessage("下载中 " + i * 2 + "%");
                }
                for (int i = 51; i <= 100; i++) {
                    Thread.sleep(100); // 模拟安装过程
                    updateProgress(i - 50, 50);
                    updateMessage("安装中 " + (i - 50) * 2 + "%");
                }
                return null;
            }
        };

        // 绑定进度条和标签到任务的状态
        progressBar.progressProperty().bind(task.progressProperty());
        label.textProperty().bind(task.messageProperty());

        // 启动任务
        new Thread(task).start();
    }
}