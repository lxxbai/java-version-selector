package io.github.lxxbai.javaversionselector.common.util;

import javafx.stage.Stage;

public class StageUtil {

    private static Stage MAIN_STAGE;

    public static void setPrimaryStage(Stage stage) {
        MAIN_STAGE = stage;
    }

    public static Stage getPrimaryStage() {
        return MAIN_STAGE;
    }
}
