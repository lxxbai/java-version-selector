package io.github.lxxbai.jvs.control;

import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.model.SettingsViewModel;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class SystemThemeControl implements Initializable {

    @FXML
    private GridPane themeGridPane;
    @FXML
    private ToggleGroup toggleGroup;

    @Resource
    private SettingsViewModel settingsViewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //当前主题
        String currentTheme = settingsViewModel.getTheme();
        //选中
        toggleGroup.getToggles().forEach(toggle -> {
            if (toggle.getUserData().equals(currentTheme)) {
                toggle.setSelected(true);
            }
        });
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            Object userData = newValue.getUserData();
            if (Objects.isNull(userData)) {
                return;
            }
            //转成字符串
            String theme = (String) userData;
            String cssPath = "css/%s-theme.css".formatted(theme);
            GUIState.getScene().getStylesheets().remove(0);
            GUIState.getScene().getStylesheets().add(0, cssPath);
            themeGridPane.getScene().getStylesheets().remove(0);
            themeGridPane.getScene().getStylesheets().add(0, cssPath);
            settingsViewModel.saveTheme(theme);
        });
    }
}