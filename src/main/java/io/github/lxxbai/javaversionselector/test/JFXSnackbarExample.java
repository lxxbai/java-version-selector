package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JFXSnackbarExample extends Application {

    @Override
    public void start(Stage primaryStage) {

        VBox vBox = new VBox();
        // 创建一个 StackPane 作为容器
        StackPane root = new StackPane();
        // 创建 JFXSnackbar 对象
        JFXSnackbar snackbar = new JFXSnackbar(root);
        // 创建一个 JFXButton 用于触发 Snackbar
        JFXButton showSnackbarButton = new JFXButton("Show Snackbar");
        // 创建 Toast 消息
        JFXSnackbarLayout layout = new JFXSnackbarLayout("这是一条 Toast 通知", null, null);
        // 配置 Snackbar 显示的消息
        showSnackbarButton.setOnAction(e -> {
        });
        // 将按钮添加到 StackPane
        root.getChildren().add(showSnackbarButton);
        vBox.getChildren().addAll(root, new JFXButton("111"), new JFXButton("111"), new JFXButton("111"));
        // 设置 StackPane 的对齐方式，将 Snackbar 放在底部
        // StackPane.setAlignment(root, Pos.TOP_CENTER); // 中心放置按钮
        // 设置 Snackbar 显示的位置（比如顶部居中）
        // snackbar.setAlignment(Pos.TOP_CENTER);  // Snackbar 放置在顶部
        // 创建场景并显示
        Scene scene = new Scene(vBox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JFXSnackbar Example");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
