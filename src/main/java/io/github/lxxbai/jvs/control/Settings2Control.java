package io.github.lxxbai.jvs.control;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.jvs.common.util.JFXValidUtil;
import io.github.lxxbai.jvs.model.DownloadConfig;
import io.github.lxxbai.jvs.spring.FXMLController;
import io.github.lxxbai.jvs.view.model.SettingsViewModel;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author lxxbai
 */
@FXMLController
public class Settings2Control implements Initializable {

    @Resource
    private SettingsViewModel settingsViewModel;

    @FXML
    private JFXComboBox<Integer> parallelDownloadsComboBox;

    @FXML
    private JFXTextField downloadPathField;

    @FXML
    private JFXTextField jdkPathField;

    @FXML
    private JFXListView menuList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //数据绑定
        parallelDownloadsComboBox.getItems().addAll(1, 2, 3, 4, 5);
        parallelDownloadsComboBox.valueProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getParallelDownloads));
        downloadPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkSavePath));
        jdkPathField.textProperty()
                .bindBidirectional(settingsViewModel.getModelProperty().buildProperty(DownloadConfig::getJdkInstallPath));
        //校验
        JFXValidUtil.defaultValidator(downloadPathField, "请选择下载文件存放地址");
        JFXValidUtil.defaultValidator(jdkPathField, "请选择JDK文件放置地址");
    }

    @FXML
    private void chooseDownloadDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择下载文件存放地址");
        File selectedDirectory = directoryChooser.showDialog(downloadPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            downloadPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void chooseJdkDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择 JDK 文件放置地址");
        File selectedDirectory = directoryChooser.showDialog(jdkPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            jdkPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }
}