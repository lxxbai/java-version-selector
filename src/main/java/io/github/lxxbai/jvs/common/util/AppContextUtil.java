package io.github.lxxbai.jvs.common.util;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.application.HostServices;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author lxxbai
 */
public class AppContextUtil {

    private static JFXSnackbar SNACKBAR;

    private static Stage MAIN_STAGE;

    private static HostServices HOST_SERVICES;

    private AppContextUtil() {

    }

    public static void setSnackbar(JFXSnackbar snackbar) {
        SNACKBAR = snackbar;
    }

    public static void sentSnackMessage(String message, Duration timeout) {
        SNACKBAR.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(message), timeout));
    }

    public static void sentSnackMessage(String message) {
        SNACKBAR.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(message), Duration.seconds(3)));
    }

    public static void setHostServices(HostServices services) {
        HOST_SERVICES = services;
    }

    public static HostServices getHostServices() {
        return HOST_SERVICES;
    }

    public static void setPrimaryStage(Stage stage) {
        MAIN_STAGE = stage;
    }

    public static Stage getPrimaryStage() {
        return MAIN_STAGE;
    }
}
