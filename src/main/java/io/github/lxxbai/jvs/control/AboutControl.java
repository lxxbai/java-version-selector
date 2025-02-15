package io.github.lxxbai.jvs.control;

import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.view.model.SettingsViewModel;
import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class AboutControl implements Initializable {

    @FXML
    private Label versionInfo;

    @Resource
    private SettingsViewModel settingsViewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        versionInfo.setText("2025 1.0.0");
    }

    public void checkVersion(ActionEvent actionEvent) {
    }
}
