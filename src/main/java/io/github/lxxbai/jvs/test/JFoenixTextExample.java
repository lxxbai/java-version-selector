
package io.github.lxxbai.jvs.test;

import io.github.lxxbai.jvs.component.SvgButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JFoenixTextExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        SvgButton svgButton = new SvgButton("svg/home.svg", 10, "添加");
        StackPane root = new StackPane();
        root.getChildren().add(svgButton);
        // 创建场景
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("JFoenix ComboBox 示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
