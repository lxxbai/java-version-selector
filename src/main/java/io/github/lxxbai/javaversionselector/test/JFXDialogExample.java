package io.github.lxxbai.javaversionselector.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
// 确保你已经导入了 JFoenix 库以使用 JFXDialog 和 JFXDialogLayout
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

public class JFXDialogExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        // 创建一个 JFXDialogLayout 实例
        JFXDialogLayout dialogLayout = new JFXDialogLayout();

        // 创建一个长文本的 Label
        Label contentLabel = new Label("这是一个非常长的文本，用于演示如何在 JFXDialogLayout 中实现 Label 的自动换行。");

        // 设置 Label 的 wrapText 属性为 true
        contentLabel.setWrapText(true);

        // 设置 Label 的最大宽度，这里设置为 300 像素
        contentLabel.setMaxWidth(300);

        // 将 Label 添加到 JFXDialogLayout 的 content 区域
        dialogLayout.setBody(contentLabel);

        // 创建一个 JFXDialog 并添加 JFXDialogLayout
        JFXDialog dialog = new JFXDialog(root,contentLabel, JFXDialog.DialogTransition.RIGHT);

        // 创建一个按钮来触发对话框显示
        JFXButton openDialogButton = new JFXButton("打开对话框");
        openDialogButton.setOnAction(e -> {
            dialog.show();
        });

        // 将按钮添加到根布局中
        root.getChildren().add(openDialogButton);
        // 设置场景和舞台
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}