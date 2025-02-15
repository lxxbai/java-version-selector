package io.github.lxxbai.jvs.spring;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.HostServices;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Objects;

/**
 * The enum {@link GUIState} stores Scene and Stage objects as singletons in
 * this VM.
 *
 * @author Felix Roske
 * @author Andreas Jay
 */
public enum GUIState {

    INSTANCE;
    private static Scene scene;

    private static Stage stage;

    private static String title;

    private static HostServices hostServices;

    private static SystemTray systemTray;

    public static String getTitle() {
        return title;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setScene(final Scene scene) {
        GUIState.scene = scene;
    }

    public static void setStage(final Stage stage) {
        GUIState.stage = stage;
    }

    public static void setTitle(final String title) {
        GUIState.title = title;
    }

    public static HostServices getHostServices() {
        return hostServices;
    }

    static void setHostServices(HostServices hostServices) {
        GUIState.hostServices = hostServices;
    }

    public static SystemTray getSystemTray() {
        return systemTray;
    }

    static void setSystemTray(SystemTray systemTray) {
        GUIState.systemTray = systemTray;
    }

    /**
     * 显示新窗口
     *
     * @param parent 父节点
     */
    public static void showInNewStage(Parent parent, String title) {
        Scene scene = parent.getScene();
        if (Objects.nonNull(scene) && scene.getWindow().isShowing()) {
            Stage currentStage = (Stage) scene.getWindow();
            currentStage.setIconified(false);
            currentStage.toFront();
            return;
        }
        Stage stage = new Stage();
        JFXDecorator decoratorView = new JFXDecorator(stage, parent, false, false, true);
        decoratorView.getStyleClass().add("new-stage-decorator");
        scene = new Scene(decoratorView);
        stage.centerOnScreen();
        stage.setTitle(title);
        // 复制旧scene的样式表到新的scene中
        scene.getStylesheets().addAll(getScene().getStylesheets());
        stage.setScene(scene);
        stage.show();
    }
}