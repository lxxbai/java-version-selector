package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTooltip;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TooltipExample extends Application {

    @Override
    public void start(Stage primaryStage) {

        JFXTooltip tooltip = new JFXTooltip("这是一个提示");
        JFXTooltip.setHoverDelay(Duration.millis(100));
        JFXButton button = new JFXButton("悬停我");
        JFXTooltip.install(button, tooltip, Pos.CENTER_RIGHT);
        // 设置场景
        HBox root = new HBox();
        root.getChildren().addAll(button);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Tooltip Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}