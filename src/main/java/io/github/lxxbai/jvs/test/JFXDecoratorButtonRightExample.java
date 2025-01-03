package io.github.lxxbai.jvs.test;

import cn.hutool.core.util.ReflectUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import io.github.lxxbai.jvs.common.util.ResourceUtil;
import io.github.lxxbai.jvs.component.LJFXDecorator;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JFXDecoratorButtonRightExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. 创建主内容区域
        BorderPane root = new BorderPane();
        Label content = new Label("This is the main content area.");
        root.setCenter(content);

        // 2. 创建自定义按钮
        JFXButton btnSettings = new JFXButton("Custom");
        btnSettings.setOnAction(e -> System.out.println("Custom button clicked!"));

        // 6. 使用 JFXDecorator 包装主内容，并设置自定义标题栏
        LJFXDecorator decorator = new LJFXDecorator(primaryStage, root, false, true, true);
        HBox buttonsContainer = (HBox) ReflectUtil.getFieldValue(decorator, "buttonsContainer");
        SVGGlyph settings = SVGGlyphLoader.loadGlyph(ResourceUtil.getUrl("svg/settings.svg"));
        settings.setFill(Color.WHITE);
        settings.setSize(13, 13);
        btnSettings = new JFXButton();
        btnSettings.getStyleClass().add("jfx-decorator-button");
        btnSettings.setCursor(Cursor.HAND);
        btnSettings.setRipplerFill(Color.WHITE);
        // btnSettings.setOnAction((action) -> maximize(resizeMin, resizeMax));
        btnSettings.setGraphic(settings);
        buttonsContainer.getChildren().add(1, btnSettings);

        // 7. 创建场景并显示
        Scene scene = new Scene(decorator, 600, 400);
        scene.getStylesheets().addAll(
                ResourceUtil.toExternalForm("css/jf-all.css")
//                ,ResourceUtil.toExternalForm("css/jfoenix-main-demo.css")
//                BootstrapFX.bootstrapFXStylesheet()
        );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
