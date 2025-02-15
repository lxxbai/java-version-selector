package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NewStageExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 主舞台（主窗口）
        Button btnOpenNewWindow = new Button("打开新窗口");
        btnOpenNewWindow.setOnAction(e -> openNewStage());

        StackPane root = new StackPane();
        root.getChildren().add(btnOpenNewWindow);

        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("主窗口");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openNewStage() {
        // 新建一个Stage
        Stage newStage = new Stage();

        // 创建按钮并添加到新窗口
        Button btnClose = new Button("关闭此窗口");
        btnClose.setOnAction(e -> newStage.close());

        StackPane root = new StackPane();
        root.getChildren().add(btnClose);

        // 创建Scene，并将其添加到Stage
        Scene scene = new Scene(root, 200, 150);
        newStage.setScene(scene);
        
        // 设置新窗口的标题
        newStage.setTitle("新窗口");

        // 显示新窗口
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}