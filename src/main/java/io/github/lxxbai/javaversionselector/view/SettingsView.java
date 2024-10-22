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
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author lxxbai
 */
@Component
public class SettingsView {

    @Resource
    private SettingsViewModel settingsViewModel;
    @Resource
    private SettingsService settingsService;
    @FXML
    public JFXComboBox<Integer> parallelDownloadsComboBox;
    @FXML
    private JFXTextField downloadPathField;
    @FXML
    private JFXTextField jdkPathField;

    @FXML
    public void initialize() {
        parallelDownloadsComboBox.getItems().addAll(1, 2, 3, 4, 5);
//        parallelDownloadsComboBox.valueProperty().bindBidirectional(settingsViewModel.parallelDownloadsProperty().asObject());
//        downloadPathField.textProperty().bindBidirectional(settingsViewModel.downloadPathProperty());
//        jdkPathField.textProperty().bindBidirectional(settingsViewModel.jdkPathProperty());
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
            JFXAlert<Void> alert = new JFXAlert<>(StageUtil.getPrimaryStage());
            closeButton.setOnAction(e -> alert.close());
            alert.setOverlayClose(false);
            alert.setContent(content);
            alert.showAndWait();
        });
    }
}