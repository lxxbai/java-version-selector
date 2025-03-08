package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXTabPane;
import io.github.lxxbai.jvs.common.util.ResourceUtil;
import io.github.lxxbai.jvs.component.SvgButton;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JFXTabPaneExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建 JFXTabPane
        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setDisableAnimation(true);
        tabPane.setSide(Side.LEFT);

        // 创建第一个 Tab
        Tab tab1 = new Tab("Tab 1");
        VBox tab1Content = new VBox(new Label("This is the content of Tab 1"));
        tab1.setContent(tab1Content);

        // 创建第二个 Tab
        Tab tab2 = new Tab();
        tab2.setText("Tab 2");
        tab2.setGraphic(new SvgButton("svg/home.svg", "Tab 2"));
        VBox tab2Content = new VBox(new Label("This is the content of Tab 2"));
        tab2.setContent(tab2Content);
        // 添加 Tabs 到 TabPane
        tabPane.getTabs().addAll(tab1, tab2);
//        tabPane.getSelectionModel().getSelectedItem().getContent()

        // 设置场景
        Scene scene = new Scene(tabPane, 600, 400);
        primaryStage.setTitle("JFXTabPane Example");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(ResourceUtil.toExternalForm("css/style.css"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}