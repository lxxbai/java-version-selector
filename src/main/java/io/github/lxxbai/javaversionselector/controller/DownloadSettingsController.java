package io.github.lxxbai.javaversionselector.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.component.XxbDialogLayout;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import io.github.lxxbai.javaversionselector.spring.FXMLController;
import io.github.lxxbai.javaversionselector.view.SettingsViewModel;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@Scope("prototype")
@FXMLController
public class DownloadSettingsController implements Initializable {

    @Resource
    private SettingsViewModel settingsViewModel;

    @FXML
    private JFXCheckBox defaultPathCheckBox;

    @FXML
    private JFXTextField jdkSavePathField;

    @FXML
    private JFXTextField jdkInstallPathField;

    @FXML
    private JFXButton downloadButton;

    @FXML
    public XxbDialogLayout downloadDialog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //配置文件绑定
        defaultPathCheckBox.selectedProperty().bindBidirectional(settingsViewModel
                .getModelProperty().buildProperty(DownloadConfig::isDefaultConfigured));
        jdkSavePathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkSavePath));
        jdkInstallPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkInstallPath));
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

    public void download() {
        if (jdkSavePathField.validate() && jdkInstallPathField.validate()) {
            settingsViewModel.save();
        }
        settingsViewModel.load();
    }

    public void cancel() {
        downloadDialog.closeSuper();
        settingsViewModel.load();
    }
}
