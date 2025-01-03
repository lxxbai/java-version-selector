package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JFXDialogExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建主场景的根布局
        StackPane root = new StackPane();

        // 创建一个按钮，点击时显示对话框
        JFXButton showButton = new JFXButton("Show Dialog");
        showButton.getStyleClass().add("button-raised");

        // 创建 JFXDialogLayout 用于设置对话框内容
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label("Dialog Heading"));
        dialogLayout.setBody(new Label("This is a message in the dialog."));

        // 创建关闭对话框的按钮
        JFXButton closeButton = new JFXButton("Close");
        closeButton.getStyleClass().addAll("button-raised", "btn-danger");

        // 创建 JFXDialog 并设置其属性
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);

        // 设置关闭按钮的动作
        closeButton.setOnAction(event -> dialog.close());

        // 将关闭按钮添加到对话框布局中
        dialogLayout.setActions(closeButton);

        // 为按钮添加点击事件，点击时显示对话框
        showButton.setOnAction(event -> {
            dialog.show();
        });

        // 将按钮添加到根布局中
        root.getChildren().add(showButton);

        // 设置场景并显示舞台
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("JFXDialog Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}