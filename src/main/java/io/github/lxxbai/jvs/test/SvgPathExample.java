package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.jvs.common.util.JFXButtonUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SvgPathExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建 Text 对象
        Text text = new Text("JDK");
        text.setFont(javafx.scene.text.Font.font("Verdana", 10));
        text.setFill(javafx.scene.paint.Color.GRAY);
        text.setX(50);
        text.setY(100);
        JFXButton jfxButton = JFXButtonUtil.buildSvgButton("svg/expand-solid.svg", "扫描本地JDK");
        // 创建 StackPane 并添加 SVGPath 和 Text
        StackPane root = new StackPane();
        root.getChildren().addAll(jfxButton, text);
        // 创建 Scene 并设置到 Stage
        Scene scene = new Scene(root, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SVGPath Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}