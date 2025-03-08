package io.github.lxxbai.jvs.test;

import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.jvs.component.SvgButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuItemExample extends Application {

    private static final String SVG_CONTENT = "M438.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L160 338.7 393.4 105.4c12.5-12.5 32.8-12.5 45.3 0z";

    @Override
    public void start(Stage primaryStage) {
        SvgButton svgButton = new SvgButton("svg/home.svg", 20, "添加");
        Scene scene = new Scene(svgButton, 300, 200);
        primaryStage.setTitle("MenuItem Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}