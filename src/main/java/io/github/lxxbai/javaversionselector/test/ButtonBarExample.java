package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTooltip;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.javaversionselector.common.util.JFXButtonUtil;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.common.util.SVGGlyphUtil;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ButtonBarExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Node node = JFXButtonUtil.buildSvgHBox("svg/trash-solid.svg", "测试");
        // 创建一个 VBox 容器
        VBox vBox = new VBox(node);
        // 创建场景
        Scene scene = new Scene(vBox, 300, 200);
        // 设置舞台
        primaryStage.setTitle("ButtonBar Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}