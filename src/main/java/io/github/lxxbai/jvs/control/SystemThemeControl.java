package io.github.lxxbai.jvs.control;

import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.model.SettingsViewModel;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class SystemThemeControl implements Initializable {

    @FXML
    private StackPane pinkTheme;
    @FXML
    private StackPane blueTheme;
    @FXML
    private StackPane blackTheme;

    @Resource
    private SettingsViewModel settingsViewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pinkTheme.setOnMouseClicked(event -> {
            GUIState.getScene().getStylesheets().remove(0);
            GUIState.getScene().getStylesheets().add(0, "css/pink-theme.css");
            pinkTheme.getScene().getStylesheets().remove(0);
            pinkTheme.getScene().getStylesheets().add(0, "css/pink-theme.css");
        });

        blueTheme.setOnMouseClicked(event -> {
            GUIState.getScene().getStylesheets().remove(0);
            GUIState.getScene().getStylesheets().add(0, "css/blue-theme.css");
            blueTheme.getScene().getStylesheets().remove(0);
            blueTheme.getScene().getStylesheets().add(0, "css/blue-theme.css");
        });

        blackTheme.setOnMouseClicked(event -> {
            GUIState.getScene().getStylesheets().remove(0);
            GUIState.getScene().getStylesheets().add(0, "css/black-theme.css");
            blackTheme.getScene().getStylesheets().remove(0);
            blackTheme.getScene().getStylesheets().add(0, "css/black-theme.css");
        });
    }
}