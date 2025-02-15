package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXToolbar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;


public class JFXToolbarExample extends Application {
    private JFXToolbar toolbar;

    private void createToolbarContent() {
        toolbar = new JFXToolbar();
        HBox toolbarContent = new HBox();
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        toolbarContent.getChildren().addAll(button1, button2);
        toolbar.getChildren().add(toolbarContent);
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        createToolbarContent();
        root.getChildren().add(toolbar);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}