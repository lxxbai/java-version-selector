package io.github.lxxbai.javaversionselector.common.util;

import javafx.application.HostServices;
import javafx.stage.Stage;

public class StageUtil {

    private static Stage MAIN_STAGE;

    private static HostServices hostServices;

    public static void setHostServices(HostServices services) {
        hostServices = services;
    }

    public static HostServices getHostServices() {
        return hostServices;
    }

    public static void setPrimaryStage(Stage stage) {
        MAIN_STAGE = stage;
    }

    public static Stage getPrimaryStage() {
        return MAIN_STAGE;
    }
}
