package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXChip;
import com.jfoenix.controls.JFXChipView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JFXChipExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个 VBox 容器
        VBox root = new VBox();
        root.setSpacing(10);

        // 创建一个标签用于说明
        Label label = new Label("以下是 JFXChip 的示例：");
        root.getChildren().add(label);

        // 创建一个 JFXChipView 来容纳多个 JFXChip
        JFXChipView<String> chipView = new JFXChipView<>();
        
        // 添加一些预定义的芯片
        chipView.getChips().addAll("Java","Python","C++");

        // 将 JFXChipView 添加到根容器中
        root.getChildren().add(chipView);

        // 设置场景并显示舞台
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("JFXChip 示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}