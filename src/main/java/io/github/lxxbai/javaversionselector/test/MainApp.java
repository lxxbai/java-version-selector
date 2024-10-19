package io.github.lxxbai.javaversionselector.test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.view.SettingsView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("主窗口");

        JFXButton settingsButton = new JFXButton("设置");
        settingsButton.setOnAction(e -> showSettingsDialog(primaryStage));

        StackPane root = new StackPane();
        root.getChildren().add(settingsButton);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSettingsDialog(Stage owner) {
        try {
            FXMLLoader loader = FXMLLoaderUtil.loadLoader("view/settings.fxml");
            StackPane settingsPane = loader.load();
            // 创建 Dialog
            JFXDialogLayout content = new JFXDialogLayout();
            content.setBody(settingsPane);

            JFXDialog dialog = new JFXDialog((StackPane) owner.getScene().getRoot(), content, JFXDialog.DialogTransition.CENTER);
            SettingsView controller = loader.getController();
            controller.setDialog(dialog);
            
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
