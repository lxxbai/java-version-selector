package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXToggleNode;
import io.github.lxxbai.jvs.component.SvgButton;
import io.github.lxxbai.jvs.component.XxbSvg;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ToggleButtonExample extends Application {

    @Override
    public void start(Stage primaryStage) {

        MenuItem menuItem = new MenuItem("选项 1");
        menuItem.setGraphic(new XxbSvg("svg/home.svg"));

        // 创建 ToggleGroup
        ToggleGroup group = new ToggleGroup();

        // 创建 ToggleButton
        JFXToggleNode tb1 = new JFXToggleNode("选项 1");
        tb1.setGraphic(new SvgButton("svg/home.svg",20));
        tb1.setToggleGroup(group);

        JFXToggleNode tb2 = new JFXToggleNode("选项 2");
        tb2.setGraphic(new XxbSvg("svg/home.svg"));
        tb2.setToggleGroup(group);

        JFXToggleNode tb3 = new JFXToggleNode("选项 3");
        tb3.setGraphic(new XxbSvg("svg/home.svg"));
        tb3.setToggleGroup(group);

        // 创建标签以显示选中的按钮
        Label selectedLabel = new Label("当前未选中任何选项");

        // 为 ToggleGroup 添加监听器
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ToggleButton selectedButton = (ToggleButton) newValue;
                selectedLabel.setText("当前选中：" + selectedButton.getText());
            } else {
                selectedLabel.setText("当前未选中任何选项");
            }
        });

        // 将控件添加到布局
        HBox vbox = new HBox(tb1, tb2, tb3, selectedLabel);
        vbox.setSpacing(10);

        // 设置场景和舞台
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ToggleButton 示例");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
