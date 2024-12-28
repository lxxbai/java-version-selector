package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author lxxbai
 */
@FXView(url = "view/download_settings.fxml")
@Component
public class DownloadView {

    @Resource
    private SettingsViewModel settingsViewModel;

    @FXML
    private JFXCheckBox defaultPathCheckBox;

    @FXML
    private JFXTextField downloadPathField;

    @FXML
    private JFXTextField jdkPathField;

    @FXML
    public void initialize() {
        //检查配置
    }

    @FXML
    private void chooseDownloadDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件存储路径");
        File selectedDirectory = directoryChooser.showDialog(downloadPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            downloadPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void chooseJdkDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件安装路径");
        File selectedDirectory = directoryChooser.showDialog(jdkPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            jdkPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }


    public void download(ActionEvent actionEvent) {
    }
}