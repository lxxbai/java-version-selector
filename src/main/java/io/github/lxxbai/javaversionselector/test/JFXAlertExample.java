package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JFXAlertExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 600, 600);

        // 创建一个主窗口
        primaryStage.setTitle("JFXAlert Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 创建 JFXAlert 对象
        JFXAlert<Void> alert = new JFXAlert<>(primaryStage);
        alert.initOwner(primaryStage); // 设置所有者窗口
        alert.setOverlayClose(false);  // 点击背景是否关闭对话框

        // 创建对话框布局
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("下载提示"));
        layout.setBody(new Label("您当前已存在相同版本的JDK，是否要重新下载？"));

        // 创建一个确认按钮
        JFXButton okButton = new JFXButton("确定");
        // 创建一个确认按钮
        JFXButton noButton = new JFXButton("不要");
        noButton.setOnAction(event -> alert.close());
        okButton.setOnAction(event -> alert.close());

        // 将按钮添加到对话框布局
        layout.setActions(okButton,noButton);
        // 设置对话框内容
        alert.setContent(layout);
        alert.setSize(200,40);
        scene.getStylesheets().addAll(ResourceUtil.toExternalForm("css/pink-theme.css"));
        // 显示对话框
        alert.showAndWait();
        // 或者使用 showAndWait() 阻塞当前线程
        // alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}