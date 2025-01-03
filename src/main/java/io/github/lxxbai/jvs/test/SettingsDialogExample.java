package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class SettingsDialogExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("主窗口");

        JFXButton settingsButton = new JFXButton("设置");
        settingsButton.setOnAction(e -> showSettingsDialog(primaryStage));

        StackPane root = new StackPane();
        root.getChildren().add(settingsButton);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSettingsDialog(Stage owner) {
        // 创建Dialog布局
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label("设置"));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // 下载任务并行数
        Label parallelDownloadsLabel = new Label("下载任务并行数:");
        JFXTextField parallelDownloadsField = new JFXTextField();
        parallelDownloadsField.setPromptText("请输入下载任务并行数");
        grid.add(parallelDownloadsLabel, 0, 0);
        grid.add(parallelDownloadsField, 1, 0);

        // 下载文件存放地址
        Label downloadPathLabel = new Label("下载文件存放地址:");
        JFXTextField downloadPathField = new JFXTextField();
        downloadPathField.setPromptText("选择下载文件存放地址");
        JFXButton downloadPathButton = new JFXButton("选择目录");
        downloadPathButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("选择下载文件存放地址");
            File selectedDirectory = directoryChooser.showDialog(owner);
            if (selectedDirectory != null) {
                downloadPathField.setText(selectedDirectory.getAbsolutePath());
            }
        });
        grid.add(downloadPathLabel, 0, 1);
        grid.add(downloadPathField, 1, 1);
        grid.add(downloadPathButton, 2, 1);

        // JDK 文件放置地址
        Label jdkPathLabel = new Label("JDK 文件放置地址:");
        JFXTextField jdkPathField = new JFXTextField();
        jdkPathField.setPromptText("选择 JDK 文件放置地址");
        JFXButton jdkPathButton = new JFXButton("选择目录");
        jdkPathButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("选择 JDK 文件放置地址");
            File selectedDirectory = directoryChooser.showDialog(owner);
            if (selectedDirectory != null) {
                jdkPathField.setText(selectedDirectory.getAbsolutePath());
            }
        });
        grid.add(jdkPathLabel, 0, 2);
        grid.add(jdkPathField, 1, 2);
        grid.add(jdkPathButton, 2, 2);

        content.setBody(grid);
        // 添加关闭按钮
        JFXButton closeButton = new JFXButton("关闭");
        content.setActions(closeButton);
        // 创建并显示Dialog
        JFXDialog dialog = new JFXDialog((StackPane) owner.getScene().getRoot(), content, JFXDialog.DialogTransition.CENTER);
        // 防止在弹框外点击时关闭
        dialog.setOnDialogClosed(e -> {
            // 如果需要，可以在这里添加其他关闭逻辑
        });
        closeButton.setOnAction(e -> dialog.close());
        dialog.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
