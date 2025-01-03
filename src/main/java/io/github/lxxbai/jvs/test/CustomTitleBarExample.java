package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXDecorator;
import io.github.lxxbai.jvs.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomTitleBarExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(true);
        // 主内容区域
        StackPane root = new StackPane();
        // 创建 JFXDecorator 并将自定义标题栏和内容区域整合
        JFXDecorator decorator = new JFXDecorator(primaryStage, root, true, true, false);
        // 设置场景和样式
        Scene decoratorScene = new Scene(decorator, 400, 300);
        decoratorScene.getStylesheets().add(  ResourceUtil.toExternalForm("css/jf-all.css"));
        primaryStage.setScene(decoratorScene);
        primaryStage.setTitle("Custom Title Bar with Settings");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
