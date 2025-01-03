package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        // 创建最小化按钮
        Button minimizeButton = new Button("Minimize");
        minimizeButton.setOnAction(event -> primaryStage.setIconified(true));

        // 创建设置按钮
        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(event -> System.out.println("Settings clicked"));

        // 使用 HBox 来布局按钮
        HBox buttonBox = new HBox(minimizeButton, settingsButton);
        buttonBox.setSpacing(200); // 设置按钮之间的间距

        // 创建 JFXDecorator
        JFXDecorator decorator = new JFXDecorator(primaryStage, root);
        decorator.setBorder(Border.EMPTY);
        Scene decoratorScene = new Scene(decorator, 400, 300);

        primaryStage.setScene(decoratorScene);
        primaryStage.setTitle("JFXDecorator Example");
        primaryStage.initStyle(StageStyle.UNDECORATED); // 可选择是否使用无边框样式
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
