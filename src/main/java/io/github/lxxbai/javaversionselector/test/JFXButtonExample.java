package io.github.lxxbai.javaversionselector.test;

import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class JFXButtonExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个 JFXButton
        JFXButton button = new JFXButton("Click Me");
        // 设置按钮的样式
        button.getStyleClass().add("custom-button");
        button.setOnAction(event -> {
            System.out.println("Button clicked!");
        });
        FontIcon warnIcon = new FontIcon(MaterialDesign.MDI_DELETE);
        JFXButton button1 = new JFXButton("Click Me");
        button1.setGraphic(warnIcon);

        // 创建一个 Label 用于显示消息
        Label label = new Label("Hello, JFoenix!");

        // 创建一个 VBox 容器，用于布局管理
        VBox vbox = new VBox(10); // 10 是组件之间的间距
        vbox.getChildren().addAll(label, button,button1);

        // 创建一个 Scene 并设置其根节点为 VBox
        Scene scene = new Scene(vbox, 300, 200);
        // 加载自定义的 CSS 文件
        scene.getStylesheets().add(ResourceUtil.toExternalForm("css/styles.css"));
        // 设置舞台的标题和场景
        primaryStage.setTitle("JFXButton Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}