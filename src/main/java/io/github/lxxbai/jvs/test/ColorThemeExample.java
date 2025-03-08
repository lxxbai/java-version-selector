package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ColorThemeExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 标题
        Label titleLabel = new Label("主题颜色");
        
        // 使用 FlowPane 让按钮按流式布局排列
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);  // 水平间距
        flowPane.setVgap(10);  // 垂直间距

        // ToggleGroup 用于单选
        ToggleGroup colorGroup = new ToggleGroup();

        // 创建多个 ToggleButton，并设置对应的背景颜色
        ToggleButton zinc     = createColorButton("Zinc",    "#333333", colorGroup);
        ToggleButton slate    = createColorButton("Slate",   "#555555", colorGroup);
        ToggleButton stone    = createColorButton("Stone",   "#777777", colorGroup);
        ToggleButton gray     = createColorButton("Gray",    "#999999", colorGroup);
        ToggleButton neutral  = createColorButton("Neutral", "#AAAAAA", colorGroup);
        ToggleButton red      = createColorButton("Red",     "#FF4D4F", colorGroup);
        ToggleButton rose     = createColorButton("Rose",    "#FF5C8A", colorGroup);
        ToggleButton orange   = createColorButton("Orange",  "#FFA500", colorGroup);
        ToggleButton green    = createColorButton("Green",   "#66CC66", colorGroup);
        ToggleButton blue     = createColorButton("Blue",    "#3399FF", colorGroup);
        ToggleButton yellow   = createColorButton("Yellow",  "#FFFF66", colorGroup);
        ToggleButton violet   = createColorButton("Violet",  "#CC99FF", colorGroup);

        // 将所有按钮添加到 FlowPane
        flowPane.getChildren().addAll(
                zinc, slate, stone, gray, neutral,
                red, rose, orange, green, blue, yellow, violet
        );

        // 整体使用 VBox 垂直排列：上面是标题，下面是颜色按钮区域
        VBox root = new VBox(10, titleLabel, flowPane);

        // 创建场景并展示
        Scene scene = new Scene(root, 500, 250);
        primaryStage.setTitle("JavaFX 主题颜色示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 工具方法：创建带背景色的 ToggleButton
     *
     * @param text       按钮文字
     * @param color      按钮背景色（如 "#FF0000"）
     * @param toggleGroup 挂载到哪个 ToggleGroup
     * @return 生成的 ToggleButton
     */
    private ToggleButton createColorButton(String text, String color, ToggleGroup toggleGroup) {
        ToggleButton button = new ToggleButton(text);
        button.setToggleGroup(toggleGroup);
        // 简单设置背景色和文字颜色
        button.setStyle("-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;");
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
