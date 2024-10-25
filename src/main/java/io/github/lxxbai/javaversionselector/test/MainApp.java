package io.github.lxxbai.javaversionselector.test;

import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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