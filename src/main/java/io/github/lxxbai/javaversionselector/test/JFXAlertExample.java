package io.github.lxxbai.javaversionselector.test;

import io.github.lxxbai.javaversionselector.common.util.AlertUtil;
import io.github.lxxbai.javaversionselector.common.util.JFXAlertUtil;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.scene.Scene;
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
        scene.getStylesheets().addAll(ResourceUtil.toExternalForm("css/alert.css"));
       JFXAlertUtil.showError(primaryStage, "下载提示", "您当前已存在相同版本的JDK，是否要重新下载？");
        AlertUtil.showError(primaryStage, "下载提示", "您当前已存在相同版本的JDK，是否要重新下载？","ss");
    }

    public static void main(String[] args) {
        launch(args);
    }
}