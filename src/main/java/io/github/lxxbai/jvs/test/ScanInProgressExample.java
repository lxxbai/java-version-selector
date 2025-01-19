package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class ScanInProgressExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("扫描中...");

        // 创建UI组件
        Label statusLabel = new Label("正在扫描...");
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setProgress(-1); // 设置为无限模式

        VBox vBox = new VBox(statusLabel, progressBar);
        
        // 创建场景并显示
        Scene scene = new Scene(vBox, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

        // 定义后台任务
        Task<Void> scanTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Path folderPath = Paths.get("D:\\"); // 替换为目标文件夹路径
                
                AtomicInteger totalFiles = new AtomicInteger(0);
                
                // 计算总文件数
                Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        totalFiles.incrementAndGet();
                        return FileVisitResult.CONTINUE;
                    }
                });

                // 模拟处理每个文件
                AtomicInteger processedFiles = new AtomicInteger(0);
                Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        try {
                            // 在这里进行实际的文件处理操作
                            Thread.sleep(10); // 模拟耗时操作
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        updateMessage("已扫描 " + processedFiles.incrementAndGet() + " / " + totalFiles.get() + " 文件");
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) {
                        System.err.println("无法访问文件: " + file.toString());
                        return FileVisitResult.CONTINUE;
                    }
                });

                return null;
            }

            @Override
            protected void succeeded() {
                statusLabel.setText("扫描完成！");
                progressBar.setVisible(false);
            }

            @Override
            protected void failed() {
                statusLabel.setText("扫描失败！");
                progressBar.setVisible(false);
            }
        };

        // 绑定消息到StatusLabel
        statusLabel.textProperty().bind(scanTask.messageProperty());

        // 运行任务
        new Thread(scanTask).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}