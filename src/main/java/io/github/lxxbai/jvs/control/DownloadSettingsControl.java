package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.jvs.common.util.SVGGlyphUtil;
import io.github.lxxbai.jvs.model.DownloadConfig;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.view.model.SettingsViewModel;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class DownloadSettingsControl implements Initializable {

    @Resource
    private SettingsViewModel settingsViewModel;

    @FXML
    private JFXCheckBox defaultPathCheckBox;

    @FXML
    private Label jdkSavePathField;

    @FXML
    private Label jdkInstallPathField;

    @FXML
    private Label headLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph("svg/settings.svg");
        svgGlyph.setSize(20,20);
        headLabel.setGraphic(svgGlyph);
        //配置文件绑定
        defaultPathCheckBox.selectedProperty().bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::isDefaultConfigured));
        jdkSavePathField.textProperty().bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkSavePath));
        jdkInstallPathField.textProperty().bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkInstallPath));
    }

    @FXML
    private void chooseDownloadDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件存储路径");
        File selectedDirectory = directoryChooser.showDialog(jdkSavePathField.getScene().getWindow());
        if (selectedDirectory != null) {
            jdkSavePathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void chooseJdkDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件安装路径");
        File selectedDirectory = directoryChooser.showDialog(jdkInstallPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            jdkInstallPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }
}
