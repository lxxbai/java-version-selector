package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class IkonliExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个 FontAwesome 图标
        FontIcon icon = new FontIcon();
        icon.setIconCode(FontAwesomeSolid.QRCODE); // 设置图标代码
        icon.setIconSize(20);

        // 创建一个描述标签
        Label description = new Label("Heart Icon");

        // 使用 VBox 布局容器将图标和描述垂直排列
        VBox vBox = new VBox(icon, description);
        vBox.setSpacing(5); // 设置图标和描述之间的间距

        // 创建一个按钮，并将 VBox 添加到按钮中
        Button button = new Button();
        button.setGraphic(vBox);

        // 创建场景
        StackPane root = new StackPane();
        root.getChildren().add(button);
        Scene scene = new Scene(root, 300, 250);

        // 设置舞台
        primaryStage.setTitle("Ikonli Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}