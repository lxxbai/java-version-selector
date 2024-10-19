package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.*;
import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import io.github.lxxbai.javaversionselector.service.SettingsService;
import jakarta.annotation.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class SettingsView {

    @Resource
    private SettingsViewModel settingsViewModel;

    @Resource
    private SettingsService settingsService;

    @FXML
    private JFXTextField parallelDownloadsField;
    @FXML
    private JFXTextField downloadPathField;
    @FXML
    private JFXTextField jdkPathField;

    private JFXDialog dialog;

    public void setDialog(JFXDialog dialog) {
        this.dialog = dialog;
    }

    @FXML
    private void chooseDownloadDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择下载文件存放地址");
        File selectedDirectory = directoryChooser.showDialog((Stage) downloadPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            downloadPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void chooseJdkDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择 JDK 文件放置地址");
        File selectedDirectory = directoryChooser.showDialog((Stage) jdkPathField.getScene().getWindow());
        if (selectedDirectory != null) {
            jdkPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void closeDialog() {
        if (dialog != null) {
            dialog.close();
        }
    }

    public void checkConfig() {
        //检查配置
        Platform.runLater(() -> {
            //检查是否有配置文件
            boolean configured = settingsService.configured();
            if (configured) {
                return;
            }
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Label("设置"));
            // 未配置，或者首次配置，显示提示框
            Node node = FXMLLoaderUtil.load("view/settings.fxml");
            content.setBody(node);
            // 添加关闭按钮
            JFXButton closeButton = new JFXButton("关闭");
            content.setActions(closeButton);
            JFXAlert alert = new JFXAlert(StageUtil.getPrimaryStage());
            alert.setOverlayClose(false);
            alert.setContent(content);
            alert.showAndWait();
        });
    }
}