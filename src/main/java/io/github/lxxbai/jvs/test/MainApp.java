package io.github.lxxbai.jvs.test;

import io.github.lxxbai.jvs.common.util.FXMLLoaderUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = FXMLLoaderUtil.loadLoader("view/test.fxml");
        Parent load = fxmlLoader.load();
        // 创建一个场景
        Scene scene = new Scene(load, 600, 400);
        // 设置舞台（窗口）的标题
        primaryStage.setTitle("JavaFX 示例");
        // 设置舞台的场景
        primaryStage.setScene(scene);

        // 显示舞台
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}