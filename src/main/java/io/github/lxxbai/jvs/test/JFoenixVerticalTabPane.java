package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JFoenixVerticalTabPane extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 创建 JFoenix TabPane
        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setSide(Side.LEFT); // 选项卡放在左侧

        // 创建 Tab
        Tab tab1 = new Tab("Tab 1", new StackPane(new Label("内容 1")));
        Tab tab2 = new Tab("Tab 2", new StackPane(new Label("内容 2")));
        Tab tab3 = new Tab("Tab 3", new StackPane(new Label("内容 3")));

        // 添加到 TabPane
        tabPane.getTabs().addAll(tab1, tab2, tab3);

        // 设置场景
        Scene scene = new Scene(tabPane, 400, 300);
        primaryStage.setTitle("JFoenix 纵向选项卡");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
